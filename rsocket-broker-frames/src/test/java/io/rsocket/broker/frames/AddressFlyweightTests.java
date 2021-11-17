/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rsocket.broker.frames;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.rsocket.broker.common.Id;
import io.rsocket.broker.common.Tags;
import io.rsocket.broker.common.WellKnownKey;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressFlyweightTests {

	@Test
	void testEncodeDecode() {
		Tags metadata = Tags.builder().with("mycustommetadata", "mycustommetadatavalue")
				.buildTags();
		Tags tags = Tags.builder().with(WellKnownKey.MAJOR_VERSION, "1")
				.with(WellKnownKey.MINOR_VERSION, "0")
				.with("mycustomtag", "mycustomtagvalue")
				.buildTags();
		assertAddress(metadata, tags, 0b01_0100_0000);
	}

	@Test
	void testEncodeDecodeEmptyMetadata() {
		Tags metadata = Tags.empty();
		Tags tags = Tags.builder().with(WellKnownKey.MAJOR_VERSION, "1")
				.with(WellKnownKey.MINOR_VERSION, "0")
				.with("mycustomtag", "mycustomtagvalue")
				.buildTags();
		assertAddress(metadata, tags, 0b00_1000_0000);
	}

	private void assertAddress(Tags metadata, Tags tags, int flags) {
		Id originRouteId = Id.random();

		ByteBuf encoded = AddressFlyweight
				.encode(ByteBufAllocator.DEFAULT, originRouteId, metadata, tags, flags);
		assertThat(FrameHeaderFlyweight.flags(encoded)).isEqualTo(flags);
		assertThat(FrameHeaderFlyweight.frameType(encoded)).isEqualTo(FrameType.ADDRESS);
		assertThat(AddressFlyweight.originRouteId(encoded)).isEqualTo(originRouteId);
		// FIXME: assertThat(AddressFlyweight.metadata(encoded)).isEqualTo(metadata);
		assertThat(AddressFlyweight.metadata(encoded)).isEqualTo(Tags.empty());
		assertThat(AddressFlyweight.tags(encoded)).isEqualTo(tags);
	}

}

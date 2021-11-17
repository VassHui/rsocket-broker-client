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

class BrokerInfoFlyweightTests {

	@Test
	void testEncodeDecode() {
		Id brokerId = Id.random();
		long timestamp = System.currentTimeMillis();
		Tags tags = Tags.builder().with(WellKnownKey.MAJOR_VERSION, "1")
				.with(WellKnownKey.MINOR_VERSION, "0")
				.with("mycustomtag", "mycustomtagvalue")
				.buildTags();
		ByteBuf encoded = BrokerInfoFlyweight
				.encode(ByteBufAllocator.DEFAULT, brokerId, timestamp, tags, 0);
		assertThat(FrameHeaderFlyweight.frameType(encoded)).isEqualTo(FrameType.BROKER_INFO);
		assertThat(BrokerInfoFlyweight.brokerId(encoded)).isEqualTo(brokerId);
		assertThat(BrokerInfoFlyweight.timestamp(encoded)).isEqualTo(timestamp);
		assertThat(BrokerInfoFlyweight.tags(encoded)).isEqualTo(tags);
	}

}

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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FrameHeaderFlyweightTests {

	@Test
	void testEncoding() {
		int flags = 0b01_0100_0000;
		ByteBuf encoded = FrameHeaderFlyweight.encode(ByteBufAllocator.DEFAULT, FrameType.ROUTE_SETUP, flags);
		assertThat(FrameHeaderFlyweight.majorVersion(encoded)).isEqualTo(FrameHeaderFlyweight.MAJOR_VERSION);
		assertThat(FrameHeaderFlyweight.minorVersion(encoded)).isEqualTo(FrameHeaderFlyweight.MINOR_VERSION);
		assertThat(FrameHeaderFlyweight.flags(encoded)).isEqualTo(flags);
		assertThat(FrameHeaderFlyweight.frameType(encoded)).isEqualTo(FrameType.ROUTE_SETUP);
	}

}

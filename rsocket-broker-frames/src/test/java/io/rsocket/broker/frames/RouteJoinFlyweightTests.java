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

class RouteJoinFlyweightTests {

	@Test
	void testEncodeDecode() {
		Id brokerId = Id.random();
		Id routeId = Id.random();
		long timestamp = System.currentTimeMillis();
		String serviceName = "myService";
		Tags tags = Tags.builder().with(WellKnownKey.MAJOR_VERSION, "1")
				.with(WellKnownKey.MINOR_VERSION, "0")
				.with("mycustomtag", "mycustomtagvalue")
				.buildTags();
		ByteBuf encoded = RouteJoinFlyweight
				.encode(ByteBufAllocator.DEFAULT, brokerId, routeId, timestamp, serviceName, tags, 0);
		assertThat(FrameHeaderFlyweight.frameType(encoded)).isEqualTo(FrameType.ROUTE_JOIN);
		assertThat(RouteJoinFlyweight.brokerId(encoded)).isEqualTo(brokerId);
		assertThat(RouteJoinFlyweight.routeId(encoded)).isEqualTo(routeId);
		assertThat(RouteJoinFlyweight.timestamp(encoded)).isEqualTo(timestamp);
		assertThat(RouteJoinFlyweight.serviceName(encoded)).isEqualTo(serviceName);
		assertThat(RouteJoinFlyweight.tags(encoded)).isEqualTo(tags);
	}

}

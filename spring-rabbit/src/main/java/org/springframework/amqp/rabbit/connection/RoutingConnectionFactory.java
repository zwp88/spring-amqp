/*
 * Copyright 2015-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.amqp.rabbit.connection;

import org.jspecify.annotations.Nullable;

/**
 * Implementations select a connection factory based on a supplied key.
 *
 * @author Gary Russell
 * @since 1.4.5
 *
 */
@FunctionalInterface
public interface RoutingConnectionFactory {

	/**
	 * Returns the {@link ConnectionFactory} bound to given lookup key, or null if one does not exist.
	 * @param key The lookup key to which the {@link ConnectionFactory} is bound
	 * @return the {@link ConnectionFactory} bound to the given lookup key, or null if one does not exist
	 */
	@Nullable
	ConnectionFactory getTargetConnectionFactory(Object key);

}

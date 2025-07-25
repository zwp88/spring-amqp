/*
 * Copyright 2002-present the original author or authors.
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

package org.springframework.amqp.rabbit.core;

import java.util.Map;

import org.jspecify.annotations.Nullable;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.core.MessageReceivingOperations;
import org.springframework.messaging.core.MessageRequestReplyOperations;
import org.springframework.messaging.core.MessageSendingOperations;

/**
 * A specialization of {@link MessageSendingOperations} and {@link MessageRequestReplyOperations}
 * for AMQP related operations that allow to specify not only the exchange but also the
 * routing key to use.
 *
 * @author Stephane Nicoll
 * @author Artem Bilan
 *
 * @since 1.4
 *
 * @see org.springframework.amqp.rabbit.core.RabbitTemplate
 */
public interface RabbitMessageOperations extends MessageSendingOperations<String>,
		MessageReceivingOperations<String>, MessageRequestReplyOperations<String> {

	/**
	 * Send a message to a specific exchange with a specific routing key.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param message the message to send
	 * @throws MessagingException a messaging exception.
	 */
	void send(@Nullable String exchange, @Nullable String routingKey, Message<?> message) throws MessagingException;

	/**
	 * Convert the given Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * wrap it as a message and send it to a specific exchange with a
	 * specific routing key.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param payload the Object to use as payload
	 * @throws MessagingException a messaging exception.
	 */
	default void convertAndSend(@Nullable String exchange, @Nullable String routingKey, Object payload)
			throws MessagingException {

		convertAndSend(exchange, routingKey, payload, null, null);
	}

	/**
	 * Convert the given Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * wrap it as a message with the given headers and send it to a
	 * specific exchange with a specific routing key.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param payload the Object to use as payload
	 * @param headers headers for the message to send
	 * @throws MessagingException a messaging exception.
	 */
	default void convertAndSend(@Nullable String exchange, @Nullable String routingKey, Object payload,
			@Nullable Map<String, Object> headers) throws MessagingException {

		convertAndSend(exchange, routingKey, payload, headers, null);
	}

	/**
	 * Convert the given Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * wrap it as a message, apply the given post processor, and send
	 * the resulting message to a specific exchange with a specific
	 * routing key.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param payload the Object to use as payload
	 * @param postProcessor the post processor to apply to the message
	 * @throws MessagingException a messaging exception.
	 */
	default void convertAndSend(@Nullable String exchange, @Nullable String routingKey, Object payload,
			@Nullable MessagePostProcessor postProcessor) throws MessagingException {

		convertAndSend(exchange, routingKey, payload, null, postProcessor);
	}

	/**
	 * Convert the given Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * wrap it as a message with the given headers, apply the given post processor,
	 * and send the resulting message to a specific exchange with a specific
	 * routing key.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param payload the Object to use as payload
	 * @param headers headers for the message to send
	 * @param postProcessor the post processor to apply to the message
	 * @throws MessagingException a messaging exception.
	 */
	void convertAndSend(@Nullable String exchange, @Nullable String routingKey, Object payload,
			@Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor)
			throws MessagingException;

	/**
	 * Send a request message to a specific exchange with a specific routing key and
	 * wait for the reply.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param requestMessage the message to send
	 * @return the reply, possibly {@code null} if the message could not be received,
	 * for example due to a timeout
	 * @throws MessagingException a messaging exception.
	 */
	@Nullable
	Message<?> sendAndReceive(@Nullable String exchange, @Nullable String routingKey, Message<?> requestMessage)
			throws MessagingException;

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter}, send
	 * it as a {@link Message} to a specific exchange with a specific routing key,
	 * receive the reply and convert its body of the specified target class.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param request payload for the request message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @param <T> return type
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 * @throws MessagingException a messaging exception.
	 */
	default <T> @Nullable T convertSendAndReceive(@Nullable String exchange, @Nullable String routingKey,
			Object request, Class<T> targetClass) throws MessagingException {

		return convertSendAndReceive(exchange, routingKey, request, null, targetClass);
	}

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter}, send
	 * it as a {@link Message} with the given headers, to a specific exchange
	 * with a specific routing key, receive the reply and convert its body of
	 * the specified target class.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param request payload for the request message to send
	 * @param headers headers for the request message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @param <T> return type
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 * @throws MessagingException a messaging exception.
	 */
	default <T> @Nullable T convertSendAndReceive(@Nullable String exchange, @Nullable String routingKey,
			Object request, @Nullable Map<String, Object> headers, Class<T> targetClass) throws MessagingException {

		return convertSendAndReceive(exchange, routingKey, request, headers, targetClass, null);
	}

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * apply the given post processor and send the resulting {@link Message} to
	 * a specific exchange with a specific routing key, receive the reply and
	 * convert its body of the given target class.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param request payload for the request message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @param requestPostProcessor post process to apply to the request message
	 * @param <T> return type
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 * @throws MessagingException a messaging exception.
	 */
	default <T> @Nullable T convertSendAndReceive(@Nullable String exchange, @Nullable String routingKey,
			Object request, Class<T> targetClass, @Nullable MessagePostProcessor requestPostProcessor)
			throws MessagingException {

		return convertSendAndReceive(exchange, routingKey, request, null, targetClass, requestPostProcessor);
	}

	/**
	 * Convert the given request Object to serialized form, possibly using a
	 * {@link org.springframework.messaging.converter.MessageConverter},
	 * wrap it as a message with the given headers, apply the given post processor
	 * and send the resulting {@link Message} to a specific exchange with a
	 * specific routing key, receive  the reply and convert its body of the
	 * given target class.
	 * @param exchange the name of the exchange
	 * @param routingKey the routing key
	 * @param request payload for the request message to send
	 * @param headers headers for the message to send
	 * @param targetClass the target type to convert the payload of the reply to
	 * @param requestPostProcessor post process to apply to the request message
	 * @param <T> return type
	 * @return the payload of the reply message, possibly {@code null} if the message
	 * could not be received, for example due to a timeout
	 * @throws MessagingException a messaging exception.
	 */
	<T> @Nullable T convertSendAndReceive(@Nullable String exchange, @Nullable String routingKey, Object request,
			@Nullable Map<String, Object> headers, Class<T> targetClass,
			@Nullable MessagePostProcessor requestPostProcessor) throws MessagingException;

}

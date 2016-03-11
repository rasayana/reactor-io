/*
 * Copyright (c) 2011-2016 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactor.io.netty.http;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import reactor.core.publisher.Mono;
import reactor.io.ipc.ChannelFlux;
import reactor.io.netty.http.model.Cookie;
import reactor.io.netty.http.model.HttpHeaders;
import reactor.io.netty.http.model.Method;
import reactor.io.netty.http.model.Protocol;
import reactor.io.netty.http.model.ResponseHeaders;
import reactor.io.netty.http.model.Status;
import reactor.io.netty.http.model.Transfer;

/**
 *
 * An Http Reactive Channel with several accessor related to HTTP flow : headers, params,
 * URI, method, websocket...
 *
 * @author Stephane Maldini
 * @since 2.5
 */
public interface HttpChannel<IN, OUT> extends ChannelFlux<IN, OUT> {

	String WS_SCHEME    = "ws";
	String WSS_SCHEME   = "wss";
	String HTTP_SCHEME  = "http";
	String HTTPS_SCHEME = "https";

	/**
	 *
	 * @return
	 */
	Map<String, Object> params();

	/**
	 *
	 * @param key
	 * @return
	 */
	Object param(String key);

	/**
	 * @return Resolved HTTP request headers
	 */
	HttpHeaders headers();

	/**
	 * @return Resolved HTTP cookies
	 */
	Map<String, Set<Cookie>> cookies();

	/**
	 * add the passed cookie
	 * @return this
	 */
	HttpChannel<IN, OUT> addCookie(String name, Cookie cookie);


	/**
	 * add the passed cookie
	 * @return this
	 */
	HttpChannel<IN, OUT> addResponseCookie(String name, Cookie cookie);


	/**
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	HttpChannel<IN, OUT> header(String name, String value);

	/**
	 * Is the request keepAlive
	 * @return is keep alive
	 */
	boolean isKeepAlive();

	/**
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	HttpChannel<IN, OUT> addHeader(String name, String value);

	/**
	 * set the request keepAlive if true otherwise remove the existing connection keep
	 * alive header
	 * @return is keep alive
	 */
	HttpChannel<IN, OUT> keepAlive(boolean keepAlive);

	/**
	 * @return the resolved request protocol (HTTP 1.1 etc)
	 */
	Protocol protocol();

	/**
	 * @return the resolved target address
	 */
	String uri();

	/**
	 * @return the resolved request method (HTTP 1.1 etc)
	 */
	Method method();

	HttpChannel<IN, OUT> paramsResolver(Function<? super String, Map<String, Object>> headerResolver);

	/**
	 * @return the resolved HTTP Response Status
	 */
	Status responseStatus();

	HttpChannel<IN, OUT> responseStatus(Status status);

	/**
	 * @return the resolved response HTTP headers
	 */
	ResponseHeaders responseHeaders();

	/**
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	HttpChannel<IN, OUT> responseHeader(String name, String value);

	/**
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	HttpChannel<IN, OUT> addResponseHeader(String name, String value);

	/**
	 *
	 * @return
	 */
	Mono<Void> writeHeaders();

	/**
	 *
	 * @return
	 */
	HttpChannel<IN, OUT> sse();

	/**
	 * @return the Transfer setting for this http connection (e.g. event-stream)
	 */
	Transfer transfer();

	/**
	 * Define the Transfer mode for this http connection
	 * @param transfer the new transfer mode
	 * @return this
	 */
	HttpChannel<IN, OUT> transfer(Transfer transfer);

	/**
	 *
	 * @return
	 */
	boolean isWebsocket();
}

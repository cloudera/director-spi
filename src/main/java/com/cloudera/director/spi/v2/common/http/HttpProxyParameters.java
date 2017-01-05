// (c) Copyright 2015 Cloudera, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cloudera.director.spi.v2.common.http;

import static com.cloudera.director.spi.v2.util.Preconditions.checkArgument;

/**
 * Houses and validates HTTP proxy parameters.
 */
public class HttpProxyParameters {

  private final String host;
  private final int port;
  private final String username;
  private final String password;
  private final String domain;
  private final String workstation;
  private final boolean preemptiveBasicProxyAuth;

  /**
   * Constructs HTTP proxy parameters with default configuration.
   */
  public HttpProxyParameters() {
    this(null, -1, null, null, null, null, false);
  }

  /**
   * Creates HTTP proxy parameters with the specified parameters.
   * <p/>
   * The host and port must be specified together.
   * <p/>
   * The username and password must also be specified together and must accompany the
   * host and port.
   * <p/>
   * The domain and workstation must be specified together and must accompany the
   * username, password, host, and port.
   *
   * @param host                     the proxy host
   * @param port                     the proxy port
   * @param username                 the proxy username
   * @param password                 the proxy password
   * @param domain                   the proxy domain (NTLM authentication only)
   * @param workstation              the proxy workstation (NTLM authentication only)
   * @param preemptiveBasicProxyAuth whether the proxy should preemptively authenticate
   */
  @SuppressWarnings("PMD.UselessParentheses")
  public HttpProxyParameters(String host, int port, String username,
      String password, String domain, String workstation,
      boolean preemptiveBasicProxyAuth) {
    this.host = host;
    this.port = port;

    checkArgument((host == null) || (this.port > 0 && this.port < 65536),
        "The supplied port must be a positive number less than 65536");

    this.username = username;
    this.password = password;

    checkArgument((username == null) == (password == null),
        "Both the proxy username and password must be supplied");
    checkArgument((host != null) || (username == null),
        "A host and port must be supplied with a username and password");

    this.domain = domain;
    this.workstation = workstation;

    checkArgument((domain == null) == (workstation == null),
        "Both the proxy domain and workstation must be supplied");
    checkArgument((username != null) || (domain == null),
        "NTLM requires a username, password, domain, and workstation to be supplied.");

    this.preemptiveBasicProxyAuth = preemptiveBasicProxyAuth;
  }

  /**
   * Returns the proxy host.
   *
   * @return the proxy host or {@code null} if not set
   */
  public String getHost() {
    return host;
  }

  /**
   * Returns the proxy port.
   *
   * @return the proxy port
   */
  public int getPort() {
    return port;
  }

  /**
   * Returns the proxy user name.
   *
   * @return the proxy user name or {@code null} if not set
   */
  public String getUsername() {
    return username;
  }

  /**
   * Return the proxy password.
   *
   * @return the proxy password or {@code null} if not set
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the proxy domain (only used in NTLM authentication).
   *
   * @return the proxy domain or {@code null} if not set
   */
  public String getDomain() {
    return domain;
  }

  /**
   * Returns the proxy workstation (only used in NTLM authentication).
   *
   * @return the proxy workstation or {@code null} if not set
   */
  public String getWorkstation() {
    return workstation;
  }

  /**
   * Returns whether the proxy should use preemptive basic authentication.
   *
   * @return whether the proxy should use preemptive basic authentication
   */
  public boolean isPreemptiveBasicProxyAuth() {
    return preemptiveBasicProxyAuth;
  }
}

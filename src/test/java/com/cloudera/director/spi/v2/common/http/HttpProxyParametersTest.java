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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class HttpProxyParametersTest {

  @Test
  public void testValidProxyConstructor() {
    constructAndVerify("host", 2, "user", "pass", "domain", "workstation", false);
    constructAndVerify("host", 2, "user", "pass", null, null, false);
    constructAndVerify("host", 2, null, null, null, null, false);
    constructAndVerify(null, -1, null, null, null, null, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProxyConstructorBadPort() {
    new HttpProxyParameters("host", -1, "user", "pass", "domain", "workstation", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProxyConstructorMissingUser() {
    new HttpProxyParameters("host", 1234, null, "pass", "domain", "workstation", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProxyConstructorMissingPassword() {
    new HttpProxyParameters("host", 1234, "user", null, "domain", "workstation", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProxyConstructorMissingDomain() {
    new HttpProxyParameters("host", 1234, "user", "pass", null, "workstation", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProxyConstructorMissingWorkstation() {
    new HttpProxyParameters("host", 1234, "user", "pass", "domain", null, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProxyConstructorSuppliedUserMissingHost() {
    new HttpProxyParameters(null, 1234, "user", "pass", "domain", "workstation", false);
  }

  /**
   * Constructs HTTP proxy parameters with the specified arguments and verifies that the
   * corresponding properties were set correctly.
   *
   * @param host                     the host
   * @param port                     the port
   * @param username                 the username
   * @param password                 the password
   * @param domain                   the domain
   * @param workstation              the workstation
   * @param preemptiveBasicProxyAuth whether to use preemptive basic authentication
   */
  private void constructAndVerify(String host, int port, String username, String password,
      String domain, String workstation, boolean preemptiveBasicProxyAuth) {
    HttpProxyParameters httpProxyParameters = new HttpProxyParameters(host, port, username,
        password, domain, workstation, preemptiveBasicProxyAuth);
    assertThat(httpProxyParameters.getHost()).isEqualTo(host);
    assertThat(httpProxyParameters.getPort()).isEqualTo(port);
    assertThat(httpProxyParameters.getUsername()).isEqualTo(username);
    assertThat(httpProxyParameters.getPassword()).isEqualTo(password);
    assertThat(httpProxyParameters.getDomain()).isEqualTo(domain);
    assertThat(httpProxyParameters.getWorkstation()).isEqualTo(workstation);
    assertThat(httpProxyParameters.isPreemptiveBasicProxyAuth()).isEqualTo(preemptiveBasicProxyAuth);
  }
}

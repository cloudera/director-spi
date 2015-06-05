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

package com.cloudera.director.spi.v1.util;

import java.io.IOException;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Methods for serializing and deserializing keys.
 */
public class KeySerialization {

  /**
   * Serializes a public key to a string.
   *
   * @param publicKey public key
   * @return string
   * @throws IOException if serialization failed
   */
  public String serialize(PublicKey publicKey) throws IOException {
    return serializeToString(publicKey);
  }

  /**
   * Serializes a private key to a string.
   *
   * @param privateKey private key
   * @return string
   * @throws IOException if serialization failed
   */
  public String serialize(PrivateKey privateKey) throws IOException {
    return serializeToString(privateKey);
  }

  /**
   * Serializes a serializable object to a Base64-encoded string.
   *
   * @param obj serializable object
   * @return string
   * @throws IOException if serialization failed
   */
  String serializeToString(Serializable obj) throws IOException {
    return Base64.encodeObject(obj);
  }

  /**
   * Deserializes a public key from a string.
   *
   * @param s string
   * @return public key
   * @throws IOException if deserialization failed
   */
  public PublicKey deserializePublicKey(String s) throws IOException {
    return deserializeFromString(s, PublicKey.class);
  }

  /**
   * Deserializes a private key from a string.
   *
   * @param s string
   * @return private key
   * @throws IOException if deserialization failed
   */
  public PrivateKey deserializePrivateKey(String s) throws IOException {
    return deserializeFromString(s, PrivateKey.class);
  }

  /**
   * Deserializes a Base64-encoded string to a (serializable) object.
   *
   * @param s string
   * @param type type of object to deserialize
   * @return serializable object
   * @throws IOException if serialization failed
   */
  <T extends Serializable> T deserializeFromString(String s, Class<T> type) throws IOException {
    try {
      return type.cast(Base64.decodeToObject(s));
    } catch (ClassNotFoundException e) {
      throw new IOException(e);
    }
  }

}

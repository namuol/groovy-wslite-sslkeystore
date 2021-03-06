/* Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wslite.soap

class SOAPMessageBuilder {

    Map xmlnsSoap = [(SOAPVersion.V1_1):'http://schemas.xmlsoap.org/soap/envelope/',
                     (SOAPVersion.V1_2):'http://www.w3.org/2003/05/soap-envelope']

    String soapNamespacePrefix = 'SOAP'
    SOAPVersion version = SOAPVersion.V1_1
    String encoding = 'UTF-8'
    Map envelopeAttributes = [:]
    Closure header = {}
    Map headerAttributes = [:]
    Closure body = {}
    Map bodyAttributes = [:]

    void version(SOAPVersion version) {
        this.version = version
    }

    void soapNamespacePrefix(String prefix) {
        this.soapNamespacePrefix = prefix
    }

    void encoding(String encoding) {
        this.encoding = encoding
    }

    void envelopeAttributes(Map attributes) {
        this.envelopeAttributes = attributes
    }

    void header(Map attributes=[:], Closure content) {
        this.header = content
        this.headerAttributes = attributes
    }

    void body(Map attributes=[:], Closure content) {
        this.body = content
        this.bodyAttributes = attributes
    }

    String toString() {
        def writer = new StringWriter()
        def soap = new groovy.xml.MarkupBuilder(writer)
        soap.mkp.xmlDeclaration(version:'1.0', encoding:encoding)
        soap."${soapNamespacePrefix}:Envelope"(["xmlns:${soapNamespacePrefix}":xmlnsSoap[version]] + envelopeAttributes) {
            "${soapNamespacePrefix}:Header"(headerAttributes, header)
            "${soapNamespacePrefix}:Body"(bodyAttributes, body)
        }
        writer.toString()
    }
}
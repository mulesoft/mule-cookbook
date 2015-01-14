CXF Java2WS Example
-------------------

This example shows how to define your services using very simple java definitions. Although we start from java the focus is still on contract first.
We simply use java as a domain specific language for service definitions. Look into the documented java sources to see how easy it really is.
After the service is defined in java we use maven and the cxf-java2ws-plugin to generate a wsdl file that describes the service.

How to execute the example?

First you prepare the project for eclipse
> mvn eclipse:eclipse

Then you might import the project into eclipse as exiting project. This allows you to use the eclipse java editor to write your services

The last step is to create the wsdl file from the service definition in java
> mvn install

You will find your wsdl in "target/generated/wsdl". You can use this wsdl file to run the second example cxfcamelexample.

Additional information can be found at 
http://www.liquid-reality.de/
http://cwiki.apache.org/confluence/display/CXF20DOC/Contract+first+webservice+with+wsdl+generation+from+java
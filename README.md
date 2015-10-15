# android-gradle-toolingapi-issue
Sample project for an issue with running android tests through gradle's tooling api using TestKit

Run `./gradlew test`. You'll notice that it will fail with

```
org.gradle.tooling.GradleConnectionException: Could not execute build using Gradle installation '/home/evan/.gradle/wrapper/dists/gradle-2.8-rc-1-all/7ljmk6udi8ej73j548cknuradm/gradle-2.8-rc-1'.
	at org.gradle.tooling.internal.consumer.ResultHandlerAdapter.onFailure(ResultHandlerAdapter.java:63)
	at org.gradle.tooling.internal.consumer.async.DefaultAsyncConsumerActionExecutor$1$1.run(DefaultAsyncConsumerActionExecuto.   ...
Caused by: org.gradle.launcher.daemon.client.DaemonConnectionException: Could not receive a message from the daemon.
	at org.gradle.launcher.daemon.client.DaemonClientConnection.receive(DaemonClientConnection.java:85)
	at org.gradle.launcher.daemon.client.DaemonClientConnection.receive(DaemonClientConnection.java:35)
	at org.gradle.launcher.daemon.client.DaemonClient.monitorBuild(DaemonClient.java:194)
	at org.gradle.launcher.daemon.client.DaemonClient.executeBuild(DaemonClient.java:162)
	at org.gradle.launcher.daemon.client.DaemonClient.execute(DaemonClient.java:125)
	at org.gradle.launcher.daemon.client.DaemonClient.execute(DaemonClient.java:80)
	at org.gradle.tooling.internal.provider.DaemonBuildActionExecuter.execute(DaemonBuildActionExecuter.java:58)
  ...
Caused by: org.gradle.messaging.remote.internal.MessageIOException: Could not read message from '/0:0:0:0:0:0:0:1%lo:34880'.
	at org.gradle.messaging.remote.internal.inet.SocketConnection.receive(SocketConnection.java:84)
	at org.gradle.launcher.daemon.client.DaemonClientConnection.receive(DaemonClientConnection.java:79)
	... 25 more
Caused by: java.io.StreamCorruptedException: invalid stream header: 636C6173
	at java.io.ObjectInputStream.readStreamHeader(ObjectInputStream.java:806)
	at java.io.ObjectInputStream.<init>(ObjectInputStream.java:299)
	at org.gradle.internal.io.ClassLoaderObjectInputStream.<init>(ClassLoaderObjectInputStream.java:27)
	at org.gradle.messaging.remote.internal.Message$ExceptionReplacingObjectInputStream.<init>(Message.java:208)
	at org.gradle.messaging.remote.internal.Message.receive(Message.java:56)
	at org.gradle.internal.serialize.BaseSerializerFactory$ThrowableSerializer.read(BaseSerializerFactory.java:148)
	at org.gradle.internal.serialize.BaseSerializerFactory$ThrowableSerializer.read(BaseSerializerFactory.java:146)
	...
```

It seems to fail as soon as the test status is reported back to gradle.


Note: this issue does not occur if you change `apply plugin:  'com.android.application'` to `apply plugin: 'com.android.library'`.

Tested from android gradle plugin `1.0.0` - `1.4.0-beta6`.
Gradle versions `2.6` - `2.8-rc-1`

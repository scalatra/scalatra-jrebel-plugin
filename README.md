JRebel plugin to reinitialize a ScalatraKernel when reloaded.

1. Install [JRebel](http://zeroturnaround.com/jrebel/)

2. Build: `mvn package`.  (We will eventually deploy to OSSRH).

3. Enable plugin in `~/bin/sbt`:

        -Drebel.plugins=/path/to/scalatra-jrebel-plugin.jar -Drebel.scalatra-jrebel-plugin=true

4. It doesn't do anything yet, but it logs where it will. :)

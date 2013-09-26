capaplan - Capacity Planning helpers
===

capaplan.rb - basic Ruby/Sinatra app to simulate various types of load common to web applications

NB: This is very early days, mostly up on Github so I can deploy easily to develop it further.

**  If you are stumbling across this and think 'urgwtf?', please bear that in mind! **

The basic idea with this is to have an easy-to-call set of operations that will utilise common web application resources:

* CPU
* Memory
* Network IO: frontend upload, download.
* Local disk IO: write, read
* Backend HTTP web services
* SQL databases
* KV stores: memcached, redis
* etc. 

From this, hopefully a basic estimate of an application can be made prior to development, to ballpark hardware resource required (or flip-side, existing system capacity)

Using HTTP endpoints allows us to use HTTP load testing toolkits like <http://gatling-tool.com Gatling>

Current endpoints:

 /status         -- return 200 OK if all good
 /load/cpu/sha1  -- sha1 10mb, to burn some CPU



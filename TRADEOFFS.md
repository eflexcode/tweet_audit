# Architecture choices

 
1. Concurrency strategy: i used batch as it better to keep track of where app  stopped
2. Error handling approach : i only allow app to crash  if key component does  not load like evn config and tweet files. also log network error
3. Performance vs safety trade-offs: Used json file as evn instead of default java evn system.
i would have to in the evn in terminal in each system which i do not want to do so instead of a  ``.evn`` file  i just used a json file

4. Why you chose your specific language/framework: java is the language i know the most


[//]: # (2. Did not use a csv any file)

[//]: # (* wanted to keep things consistence as i did not see a gain more over a json is more well-structured)
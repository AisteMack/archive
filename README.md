# If we add multiple archiving methods (7z, for example);
All archiving logic exists in the service level, so it would be easy to add another archiving method and call it based on parameter.

# Face a significant increase in request count
Application could be run on several instances. Now it is configurated only for one instance because of in memory database. 

# Allow 1GB max file size
Files are zipped with streams so size increase should not have major impact.
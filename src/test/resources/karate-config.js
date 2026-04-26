function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  
  if (!env) {
    env = 'dev';
  }
  
  // Use the port assigned by Spring Boot
  var port = karate.properties['karate.port'] || '8080';
  var protocol = 'http';
  
  var config = {
    env: env,
    baseUrl: protocol + '://localhost:' + port
  };
  
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  
  return config;
}

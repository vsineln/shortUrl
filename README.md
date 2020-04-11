# Application for short URL creation

Build and deploy:

    mvn clean package
    
    cd docker/
    
    sudo docker-compose up --build
    

API:
- Redirect by provided short URL  
`GET localhost:8081/v1/url/IUS3jOiU`

- Create short URL for provided link  
`POST localhost:8081/v1/url {"originalUrl":"https://en.wikipedia.org/wiki/Common_chiffchaff"}`

- Delete short URL  
`DELETE localhost:8081/v1/url/7Sm5MVFw`                          
                                                           
Settings for handling keys' repository:
- initial capacity for keys' repository `keys.capacity: 1000`
- default time for keeping url `keys.expiration.days 180`
- delay for the scheduler to check the number of free keys and renewing key repository if needed `keys.update.delay: 600_000`
- cron time when expired links will be removed  `keys.check.expired: 0 30 0 * * ?`

Management information about application work: http://localhost:8081/actuator/
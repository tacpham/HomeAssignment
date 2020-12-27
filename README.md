github.com/voucherifyio/voucher-code-generator-java/

Demo application for assignment v2

1. With  requirements I create some service:
- Service Discovery: I'm using Netflix Eureka as server and other microservices as client.
- API Gateway: I'm using Zuul for routing and filtering. All magic of microservices happened under the hood and user not
  know about that
- Auth Service: user need to provide a pair of username/password (password encrypted and decrypted by BCrypt) for login 
  then they can get JWT back inside header, This JWT using for authorize access to other services. I'm using Feign to get 
  user from Customer Service.
- Customer Service: An Eureka client service, it's return information about customer like customer information, contact, address
- Voucher Service: A service that returning voucher code to user. Voucher code should be linked with phone number.
- Thirdparty Service: Simple rest api service, generating and returning random voucher code. I set a waiting there to 
  adapt service will be return random from 3 to 120s.

- Remaining works:
  +/ ThirdPartyService and VoucherService should communicate through an MessageQueue. I do not have enough
  time to implement this but it should be a MUST because of the delay time between those services.
  +/ DTO validation.
  +/ Unit test.
2. Main framework using in this project is Spring boot, Spring Cloud Netflix, JPA, Mongodb for Database.
3. Step to run this project locally:
- Start EurekaServer and all other service then check Eureka Server and its client, then check the result. It's should be 
  something like "screenshot\Eureka Server.png"
4. I'm using Postman (just like CURL) for check Rest API request:
- For Login: ( please check "Login with username and password via api-gateway.png" for more visualize with result JWT token)
   + Post Method with url http://localhost:8080/auth-service
   + Post Body:
     {
       "username":"user",
       "password":"P@ssw0rd"
     }
- Get 1 voucher code with phone number: No authorization require (please check "screenshot\getVoucher with Phone Number.png")
   + Get Method with http://localhost:8080/voucher-service/getVoucher/0399642138
   + When timeout happens, I'm using Hystrix to inform to user that the request is being processed within 30 seconds. ( please 
     check screenshot\The request is being processed within 30 seconds.png). Meanwhile, I'm using Executor for save delay voucher 
     (please check screenshot\Save delay voucher with thread.png)
     
- Get All Voucher code by phone number: need JWT token (please check "screenshot\getVouchers with Phone Number.png")
   + Get Method with http://localhost:8080/voucher-service/getVouchers/0399642138

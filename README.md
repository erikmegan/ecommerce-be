# ECOMMERCE-ASSIGNMENT

Technologies:
  * Spring Boot 2 (Using <b>spring-boot-starter-webflux</b>)
  * Redis (Using <b>spring-boot-starter-data-redis</b>)
  * postgresql (Using <b>spring-boot-starter-data-jpa</b>)
  * Web (Using <b>spring-boot-starter-web</b>)
  

## infra-up Application
```
make infra-up
```

## Run Application
```
make run
```


## REST API ENDPOINT FORMAT
```
http://localhost:8080/mocks/ecommerce/assignment/
```

## User Create 
```
method : POST
path : /mocks/ecommerce/assignment/user/create
request body : 
    {
      "username": "usernameExample"
    }
success response :
    {
      "username": "asd16",
      "createdDate": "2023-09-13T03:45:39.913+00:00"
    }
failed response :
    409 Username alreaady exists
```

## User Login 
```
method : POST
path : /mocks/ecommerce/assignment/user/login
request body : 
    {
      "username": "usernameExample"
    }
success response :
{
  "token": "73c0af94-1cc2-48e7-952d-93730364d83f"
}
failed response :
    404 User not found
```

## Order Create 
```
method : POST
path : /mocks/ecommerce/assignment/order/create
header : Authorization
request body : 
    {
      "orderDetail": [
        {
          "orderCartId": 0,
          "productId": 1,
          "amount": 1,
          "notes": "string"
        }
      ]
    }
success response :
{
    "orderId": 20,
    "paymentStatus": "PAID",
    "craetedDate": "2023-09-13T03:54:57.035+00:00",
    "orderDetails": [
        {
            "orderDetailId": 21,
            "orderCartId": null,
            "productId": 1,
            "amount": 1,
            "notes": "string"
        }
    ]
}
failed response :
    404 Insufficient stock
```

## Cart Create 
```
method : POST
path : /mocks/ecommerce/assignment/cart/create
header : Authorization
request body : 
    {
      "productId": 0,
      "amount": 0,
      "notes": "string"
    }
success response :
{
  "id": 0,
  "userId": 0,
  "productId": 0,
  "productName": "string",
  "productTypeId": 0,
  "productDesc": "string",
  "amount": 0,
  "notes": "blue",
  "cartStatus": "CART"
}
failed response :
    404 Insufficient stock
```

## GET Cart 
```
method : GET
path : /mocks/ecommerce/assignment/cart/
header : Authorization
success response :
{
  "id": 0,
  "userId": 0,
  "productId": 0,
  "productName": "string",
  "productTypeId": 0,
  "productDesc": "string",
  "amount": 0,
  "notes": "blue",
  "cartStatus": "CART"
}
failed response :
    404 Not Found
```

## GET Product By Merchant Id 
```
method : GET
path : /mocks/ecommerce/assignment/product/merchant/
header : Authorization
request body : 
    {
      "merchantId": "123"
    }
success response :
{
  "id": 0,
  "userId": 0,
  "productId": 0,
  "productName": "string",
  "productTypeId": 0,
  "productDesc": "string",
  "amount": 0,
  "notes": "blue",
  "cartStatus": "CART"
}
failed response :
    404 Not Found
```

## GET Product By Product Id
```
method : GET
path : /mocks/ecommerce/assignment/product/merchant/
header : Authorization
request body : 
    {
      "produtId": "123"
    }
success response :
{
  "id": 0,
  "userId": 0,
  "productId": 0,
  "productName": "string",
  "productTypeId": 0,
  "productDesc": "string",
  "amount": 0,
  "notes": "blue",
  "cartStatus": "CART"
}
failed response :
    404 Not Found
```

## GET All Product
```
method : GET
path : /mocks/ecommerce/assignment/product/
success response :
{
  "id": 0,
  "userId": 0,
  "productId": 0,
  "productName": "string",
  "productTypeId": 0,
  "productDesc": "string",
  "amount": 0,
  "notes": "blue",
  "cartStatus": "CART"
}
failed response :
    404 Not Found
```



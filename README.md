# menu-service

The idea behind this app is to manage the necessities of a menu and its groups and products.

## resources

### menu

> POST /menu

> GET /menu/{id}

> GET /menu?customerId={id}

> __TO-DO:__ PATCH /menu/{id}

> __TO-DO:__ DELETE /menu/{id}

(delete is a cascade process)

```java
{
    "customer_id": "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
    "name": "Menu de segunda-feira",
    "description": "Menu da segunda-feira pra iniciar a semana fininho.",
    "available": true
}
```

<hr>

### group

> POST /group

> POST /group/{id}

> POST /group?menuId={id}

> __TO-DO:__ PATCH /group/{id}

> DELETE /group/{id}

(delete is a cascade process)

```java
{
    "menu_id": "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
    "name": "Sorvetinhos do fininho",
    "description": "Sorvetes delicia pro fininho viva a coca-cola!",
    "type": "ICE_CREAM",
    "available": true
}
```

<hr>

### product

> POST /product

> POST /product/{id}

> POST /product?groupId={id}

> __TO-DO:__ PATCH /product/{id}

> __TO-DO:__ DELETE /product/{id}

```java
{
{
    "group_id": "padovs",
    "name": "Virada paulista 3",
    "description": "Arroz, tutu de feijão, couve, bisteca, linguiça e banana.",
    "amount": 29.99,
    "adults_only": false,
    "available": true,
    "addons": {
        "Bisteca extra": 4.99,
        "Linguiça extra": 2.99,
        "Ovo frito": 1.99
    },
    "image_urls": [
        "https://amp.receitadevovo.com.br/wp-content/uploads/2020/10/virado-paulista.jpg",
        "https://f.i.uol.com.br/fotografia/2018/02/05/15178692315a78d8af8c8db_1517869231_3x2_rt.jpg"
    ]
}
}
```

## setup environment

### requiriments:
- Acess to a terminal
- Git
- Docker-compose version 3 or later
- Java 11 or later

### steps

- Clone the app
```sh
$ git clone git@github.com:imaitre/customer-service.git
```

- Get in the repository.
```sh
$ cd menu-service
```

- Start mongoDB 
```sh
$ docker-compose -f compose/docker-compose.yml up -d
```


- Build the project
```sh
$ ./gradlew build
```

- Run the app
```sh
$ java -jar build/libs/menuservice-0.0.1-SNAPSHOT.jar
```
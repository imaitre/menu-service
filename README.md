# menu-service

The idea behind this app is to manage the necessities of a menu and its groups and products.

## resources

### menu

> GET /menu/{id}

> GET /menu?customer_id={id}

> POST /menu

> PUT /menu/{id}

> DELETE /menu/{id}

(delete is a cascade process)

```json
{
    "customer_id": "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
    "name": "Menu de segunda-feira",
    "description": "Menu da segunda-feira pra iniciar a semana.",
    "available": true
}
```

<hr>

### group

> GET /group/{id}

> GET /group?menu_id={id}

> POST /group

> PATCH /group/{id}

> DELETE /group/{id}

(delete is a cascade process)

```json
{
    "menu_id": "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
    "name": "Sorvetes",
    "description": "Sorvetes, gelattos!",
    "type": "ICE_CREAM",
    "available": true
}
```

<hr>

### product

> GET /product/{id}

> GET /product?group_id={id}

> POST /product

> PUT /product/{id}

> DELETE /product/{id}

```json
{
    "group_id": "padovs",
    "name": "Virada paulista 3",
    "description": "Arroz, tutu de feijão, couve, bisteca, linguiça e banana.",
    "amount": 29.99,
    "adults_only": false,
    "available": true,
    "addons": [
        "OPTI_2125f0da-cd5b-4e41-87e9-0e76cb7ec40a",
        "OPTI_6525f0da-fgb-9e41-83f9-0e76cb7ec40a"
     ],
    "image_urls": [
        "https://amp.receitadevovo.com.br/wp-content/uploads/2020/10/virado-paulista.jpg",
        "https://f.i.uol.com.br/fotografia/2018/02/05/15178692315a78d8af8c8db_1517869231_3x2_rt.jpg"
    ]
}
```

### optional

> POST /opcional

> GET /opcional?menu_id={id}&available=true
 
> GET /opcional/{id}

> PUT /opcional/{id}

> DELETE /opcional/{id}


```json
{
    "id": "OPTI_2125f0da-cd5b-4e41-87e9-0e76cb7ec40a",
    "menu_id": "MENU_2125f0da-cd5b-4e41-87e9-0e76cb7ec40a",
    "name": "Cobertura extra",
    "available": true,
    "minimum": 0,
    "maximum": 2,
    "description": "Escolha duas coberturas extra no seu sorvete",
    "repeat": true,
    "addons": [
        {
            "name": "Caramelo",
            "description": "caramelo fino",
            "price": 1.00,
            "available":true,
            "id": "1222"
        },
        {
            "name": "Chocolate",
            "description": "chocolate fino",
            "price": 1.50,
            "available":true
        }
    ]
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
$ docker-compose -f docker-compose.yml up -d
```


- Build the project
```sh
$ ./gradlew build
```

- Run the app
```sh
$ java -jar build/libs/menuservice-0.0.1-SNAPSHOT.jar
```

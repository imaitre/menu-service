# menu-service

The idea behind this app is to manage the necessities of a menu and its partitions and products.

## resources

> GET /menu/{id}

> GET /menu?customerId={id}

> GET /menu/{id}/group/{id}

> GET menu/{id}/group/{id}/product/{id}

<hr>

> POST /menu

> POST /menu/{id}/group

> POST /menu/{id}/group/{id}/product

<hr>

> PATCH /menu/{id}

> PATCH /menu/{id}/group/{id}

> PATCH menu/{id}/group/{id}/product/{id}

<hr>

> DELETE /menu/{id}

> DELETE /menu/{id}/group/{id}

> DELETE menu/{id}/group/{id}/product/{id}


(delete is a cascade process)

## payload
```java
{
    "id": "MENU_69073798-dbf8-43e6-8083-bed3d26dc426",
    "customer_id": "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
    "name": "",
    "description": "",
    "available": true,
    "group": [
        {
            "id": "GROU_fec74992-5bb9-4c88-b6c3-5460a9d3aab3",
            "menu_id": "MENU_69073798-dbf8-43e6-8083-bed3d26dc426",
            "name": "",
            "description": "",
            "type": "",
            "available": true,
            "product": [
                {
                    "id": "PROD_7fba65cf-9171-48c1-a481-667f8658539e",
                    "group_id": "GROU_fec74992-5bb9-4c88-b6c3-5460a9d3aab3",
                    "name": "",
                    "description": "",
                    "amount": 0.0,
                    "adults_only": false,
                    "available": true,
                    "addons": [
                        {
                            "k": "v"
                        },
                        {
                            "e.g.: Extra sausage": 1.50
                        }
                    ],
                    "image_urls": [
                        "",
                        "",
                        ""
                    ]
                }
            ]
        }
    ]
}
```

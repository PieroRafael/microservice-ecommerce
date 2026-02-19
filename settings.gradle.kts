rootProject.name = "cloudnative-ecommerce"

// Infraestructura Core
include("config-server")
include("discovery-server")
include("api-gateway")

// Dominios de Negocio
include("product-service")
include("order-service")
//include("inventory-service")
//include("payment-service")
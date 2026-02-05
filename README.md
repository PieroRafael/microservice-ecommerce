# 🚀 "CloudNative E-Commerce" (Edición Local-Cloud)

Sistema de comercio electrónico simplificado. Con dominiios naturales como : (Inventario, Pagos, Usuarios, entre otros) que nos obligan a resolver los problemas reales de los microservicios: transacciones distribuidas, comunicación asíncrona y escalabilidad.

Objetivo : 
Creación del (E-commerce) y la infraestructura subyacente para simular AWS en nuestra máquina local sin gastar un centavo, manteniendo la calidad enterprise.

---

# 🗺️ Hoja de Ruta Actualizada (2025/2026 Standards)

## Fase 1: Fundamentos de Arquitectura y Diseño (El "Blueprint")

**Objetivo:** Diseñar el sistema con enfoque DDD. Definir fronteras claras para evitar un "monolito distribuido".

- **Tecnologías:** Diagramas C4 Model (Context, Container, Component), Event Storming.
- **Conceptos Clave:** Bounded Contexts, Ubiquitous Language, API First (OpenAPI/Swagger), 12-Factor App.

---

## Fase 2: El Ecosistema Spring Cloud (El "Core")

**Objetivo:** Construir los microservicios base y establecer la comunicación síncrona optimizada.

- **Tecnologías:**
  - **Spring Boot 3.4+:** Uso de **Virtual Threads (Project Loom)** para alta concurrencia.
  - **Spring Cloud Gateway:** API Gateway.
  - **Eureka / Consul:** Service Discovery.
  - **Spring Cloud Config:** Configuración externa.
  - **Identity Provider (IDP):** Integración con **Keycloak** o **Spring Authorization Server** para OAuth2/OIDC.
- **Reto:** Comunicación entre `Order-Service` y `Product-Service` usando OpenFeign + Seguridad JWT.

---

## Fase 3: Resiliencia y Event-Driven Architecture (Desacoplamiento)

**Objetivo:** Implementar comunicación asíncrona, patrones de tolerancia a fallos y validación de contratos.

- **Tecnologías:**
  - **Resilience4j:** Circuit Breakers, Timeouts, Retries.
  - **Kafka (vía Docker):** Broker de mensajería para eventos de dominio.
  - **Consumer-Driven Contract Testing:** Uso de **Pact** o **Spring Cloud Contract** para asegurar la interoperabilidad.
- **Reto:** Implementar el patrón Saga (Coreografía) para gestionar transacciones distribuidas entre Pedidos e Inventario.

---

## Fase 4: Contenerización y Orquestación Local (El "Mini Data Center")

**Objetivo:** Empaquetar y orquestar como en producción, pero en tu PC.

- **Tecnologías:**
  - **Docker & Jib:** Construcción optimizada de imágenes.
  - **Minikube o Kind:** Nuestro cluster de K8s gratuito y local.
  - **Helm Charts:** Para empaquetar nuestros despliegues de K8s.
- **Reto:** Desplegar todo el stack en Minikube y realizar un "Rolling Update" sin caída de servicio.

---

## Fase 5: Simulación de Cloud (AWS) e Infraestructura como Código

**Objetivo:** Aprender AWS y Terraform sin tarjeta de crédito.

- **Tecnologías:**
  - **LocalStack:** Emulador de AWS (S3, SQS, SNS, Lambda).
  - **Terraform:** Infraestructura como Código (IaC).
  - **GitHub Actions (Self-hosted):** Pipelines de CI/CD.
- **Reto:** Que tu microservicio suba imágenes de productos a un "S3 Bucket" (en LocalStack) creado automáticamente por Terraform.

---

## Fase 6: Excelencia Operativa y Modernización (2026 Standards)

**Objetivo:** : Observabilidad profunda y Performance.

- **Tecnologías:**
  - **OpenTelemetry & Grafana Stack (Loki/Tempo):** Trazabilidad distribuida y **Structured Logging** (nativo en Spring Boot 3.4).
  - **GraalVM (Opcional):** Compilar a imagen nativa para arranque instantáneo (<100ms).
- **Reto:** Diagnosticar un "cuello de botella" simulado usando métricas y trazas distribuidas.

---

## 🛠️ Stack Tecnológico Actualizado

- **Java 21 (LTS):** Requerido para Virtual Threads.
- **Spring Boot 3.4+:** Último estándar de estabilidad.
- **Gradle (Kotlin DSL):** Recomendado para mayor claridad en scripts.
- **Docker Desktop / Rancher Desktop.**
- **LocalStack.**
- **Minikube.**
- **Terraform CLI.**
- **Postman / Insomnia.**

---

# Explore With Me — микросервисная архитектура и рекомендательный сервис

## Описание

**Explore With Me** — сервис для публикации событий, подачи заявок на участие, просмотра подборок и получения персональных рекомендаций.

Проект реализован в микросервисной архитектуре.  
Внешний API доступен через **API Gateway** на порту **8080**.  
Для инфраструктуры используются:

- **Spring Cloud Gateway** — единая точка входа;
- **Eureka** — сервис обнаружения;
- **Config Server** — централизованная конфигурация;
- **OpenFeign** — HTTP-взаимодействие между сервисами;
- **gRPC** — взаимодействие со статистикой и рекомендательной подсистемой;
- **Apache Kafka** — потоковая обработка пользовательских действий;
- **PostgreSQL** — хранение данных.

---

## Архитектура

### Инфраструктурные сервисы

- **discovery-server** — Eureka Server;
- **config-server** — централизованное хранилище конфигурации;
- **gateway-server** — внешний вход в систему и маршрутизация запросов.

### Бизнес-сервисы

- **user-service** — управление пользователями;
- **catalog-service** — категории и подборки;
- **event-service** — события, публичные и административные операции;
- **request-service** — заявки на участие;
- **stats-server** — статистика просмотров.

### Сервисы рекомендательной системы

- **collector** — принимает пользовательские действия по gRPC и публикует их в Kafka;
- **aggregator** — читает действия пользователей, вычисляет сходство мероприятий и публикует результаты в Kafka;
- **analyzer** — обновляет таблицы взаимодействий и сходства, предоставляет рекомендации по gRPC.

---

## Границы ответственности сервисов

### user-service
Отвечает за:
- создание пользователей;
- получение списка пользователей;
- удаление пользователей;
- внутреннюю проверку существования пользователя;
- внутреннее получение краткой информации о пользователях.

### catalog-service
Отвечает за:
- управление категориями;
- управление подборками событий;
- публичный доступ к категориям и подборкам;
- внутреннее получение краткой информации о категориях;
- проверку возможности удаления категории через `event-service`.

### event-service
Отвечает за:
- создание и редактирование событий;
- поиск событий по публичным и административным фильтрам;
- публикацию и отклонение событий;
- получение данных событий для подборок;
- проверку использования категории;
- интеграцию со `stats-server` для просмотров;
- интеграцию с `analyzer` для персональных рекомендаций.

### request-service
Отвечает за:
- создание заявок на участие;
- отмену заявок;
- просмотр заявок пользователя;
- просмотр и модерацию заявок на события пользователя;
- внутренний расчёт количества подтверждённых заявок;
- отправку пользовательских действий в рекомендательный пайплайн через `collector`.

### stats-server
Отвечает за:
- сохранение информации о запросах (`/hit`);
- получение статистики просмотров (`/stats`).

### collector
Отвечает за:
- приём пользовательских действий по gRPC;
- отправку действий в Kafka-топик `stats.user-actions.v1`.

### aggregator
Отвечает за:
- чтение данных из `stats.user-actions.v1`;
- вычисление сходства между мероприятиями;
- отправку результатов в Kafka-топик `stats.events-similarity.v1`.

### analyzer
Отвечает за:
- чтение данных из Kafka;
- обновление таблиц истории взаимодействий пользователей и сходства мероприятий;
- выдачу рекомендованных мероприятий по gRPC.

---

## Внешний API

Все внешние запросы проходят через **gateway-server** на порту **8080**.

### Основной API
- `/admin/users`
- `/admin/categories`
- `/admin/compilations`
- `/admin/events`
- `/categories`
- `/compilations`
- `/events`
- `/events/recommendations`
- `/users/{userId}/events`
- `/users/{userId}/events/{eventId}`
- `/users/{userId}/events/{eventId}/requests`
- `/users/{userId}/requests`
- `/users/{userId}/requests/{requestId}/cancel`

### API статистики
- `POST /hit`
- `GET /stats`

---

## Внутренний API между сервисами

### user-service
Используется другими сервисами:
- `GET /internal/users/{userId}/exists`
- `GET /internal/users/{userId}/short`
- `POST /internal/users/short/batch`

### catalog-service
Используется другими сервисами:
- `GET /internal/categories/{categoryId}/exists`
- `GET /internal/categories/{categoryId}/short`
- `POST /internal/categories/short/batch`

### event-service
Используется другими сервисами:
- `GET /internal/categories/{categoryId}/used`
- `POST /internal/events/short`
- `GET /internal/events/{eventId}/request-info`

### request-service
Используется другими сервисами:
- `GET /internal/requests/events/{eventId}/confirmed-count`
- `POST /internal/requests/events/confirmed-count/batch`

### stats-server
Используется через HTTP-клиент статистики:
- `POST /hit`
- `GET /stats`

### collector / analyzer
Используются по **gRPC**:
- `collector` — приём действий пользователя;
- `analyzer` — получение рекомендованных мероприятий.

---

## Межсервисное взаимодействие

### HTTP / OpenFeign
Основные зависимости:
- `event-service -> user-service`
- `event-service -> catalog-service`
- `event-service -> request-service`
- `event-service -> stats-server`
- `catalog-service -> event-service`
- `request-service -> event-service`
- `request-service -> user-service`

### gRPC
- `request-service -> collector`
- `event-service -> analyzer`

### Kafka
Используются топики:
- `stats.user-actions.v1`
- `stats.events-similarity.v1`

---

## Как работает рекомендательный пайплайн

1. Пользователь взаимодействует с событием.
2. `request-service` фиксирует действие и отправляет его в `collector` по gRPC.
3. `collector` публикует действие в Kafka-топик `stats.user-actions.v1`.
4. `aggregator` читает действия пользователей, вычисляет сходство мероприятий и публикует результат в `stats.events-similarity.v1`.
5. `analyzer` читает оба топика и обновляет таблицы:
    - `user_event_interactions`
    - `event_similarities`
6. `event-service` запрашивает рекомендации у `analyzer` по gRPC.
7. Внешний клиент получает рекомендации через:
    - `GET /events/recommendations`

---

## Конфигурация

Конфигурации сервисов расположены в каталоге:

`infra/config-server/src/main/resources/config`

Там находятся настройки для:
- `gateway-server`
- `user-service`
- `catalog-service`
- `event-service`
- `request-service`
- `stats-server`
- `collector`
- `aggregator`
- `analyzer`

Каждый сервис получает конфигурацию через **Config Server**.

---

## Хранение данных

В проекте используются две базы данных:

- **ewm-db** — основная база бизнес-сервисов;
- **stats-db** — база статистики просмотров.

Дополнительно в `ewm-db` хранятся данные рекомендательной системы:
- история взаимодействий пользователей с мероприятиями;
- коэффициенты сходства между мероприятиями.

---

## Отказоустойчивость

Для снижения количества ошибок 5xx реализована безопасная деградация:

- если недоступен `stats-server`, количество просмотров возвращается как `0`;
- если недоступен `request-service`, количество подтверждённых заявок возвращается как `0`;
- если недоступен `user-service`, возвращается fallback-пользователь;
- если недоступен `catalog-service`, возвращается fallback-категория;
- если недоступен `event-service`, сервис подборок возвращает пустой список событий.

Также для Feign-клиентов настроены сокращённые timeout’ы.

---

## Docker-запуск

### Важно
Dockerfile сервисов копируют уже собранные jar-файлы из `target/`.  
Перед запуском контейнеров необходимо предварительно собрать модули локально через Maven или через панель Maven в IntelliJ IDEA.

### Запуск
```bash
docker compose up -d --build
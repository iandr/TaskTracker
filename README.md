# Трекер задач

## Суть
Диспетчер задач с авторизацией пользователей

## Что внутри
##### Сервер
Spring Boot, lombok, JPA

СУБД Oracle 10g. Перед запуском сервера должны быть инициализированы таблицы (server\db_initialize.sql)

##### Клиент
GWT, restygwt

## Запуск приложения
.\common> mvn install

.\server> run SimpleRestApplication.main()

.\frontend> mvn clean war:exploded gwt:devmode

## Демо
Вкладки регистрация/авторизация
![](demo\login_sign_up.gif)

Обработка некорректной авторизации
![](demo\incorrect_login_pass.gif)

Регистрация
![](demo\sign_up_success.gif)

Авторизация пользователя
![](demo\login_success.gif)

Добавление задачи
![](demo\add_task.gif)

Фильтрация
![](demo\filter.gif)

Открытие карточки задачи
![](demo\open_task.gif)

Смена исполнителя
![](demo\change_executor.gif)

Закрытие задачи
![](demo\close_task.gif)

Удаление задачи пользователем (не разрешено)
![](demo\delete_by_user.gif)

Удаление задачи администратором
![](demo\delete_by_admin.gif)

Выход из системы
![](demo\xit.gif)
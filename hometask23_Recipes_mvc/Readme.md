#Hometask23. Recipes-JPA-MVC.

Итоговое задание.
Необходимо написать приложение-книга рецептов. Каждый рецепт состоит из нескольких ингредиентов. Должна быть возможность CRUD-операций с рецептами, 
а также с ингредиентами.

Приложение реализовано на основе нескольких пакетов:
<h3>dao</h3>
Используется для сохранения сущностей предметной области-рецептов, ингредиентов, а также их связей в базу данных. Базируется на
стандарте JPA и реализации Hibernate.

<h3>service</h3>
Являются службой по работе с dao, а также по отображению entity в объеты DTO, с которыми работает клиентская часть. Помимо прочего
на этом уровне осуществляются транзакции.

<h3>controller</h3>
Нужны для отображения запросов на обработчики. Реализовано на Spring MVC Framework.

View-часть реализована на JSP с небольшими JSTL-вставками.



<h3>Запуск программы</h3>
Для запуска программы необходимо в корне модуля (в директории где находится файл pom.xml) выполнить команду терминала mvn package,
затем из папки target взять файл recipes-jpa-mvc.war и копировать его в какую-либо реализацию servlet api (для Tomcat-поместить
в папку webapps). Зайти на главную страницу http://localhost:8080/recipes-jpa-mvc/. 

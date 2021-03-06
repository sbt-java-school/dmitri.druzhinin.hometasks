#Hometask22. Recipes.

Необходимо создать приложение, манипулирующее рецептами. Каждый рецепт состоит из нескольких ингредиентов. Должна быть возможность
CRUD-операций с рецептами, а также с ингредиентами. Слой работы с БД реализован с помощью Spring-Jdbc. Использовалась СУБД H2 database
во встроенном(embedded mode) режиме.

Для первого запуска приложения необходимо в корне модуля выполнить команду mvn compile. Затем в файле target/classes/jdbc.properties
указать любую директорию куда будет поставлена база данных в формате jdbc.url=jdbc:h2:[pathToDB], например для ОС Linux
jdbc.url=jdbc:h2:/home/user/Документы/mydb, для ОС Windows jdbc.url=jdbc:h2:C:/Program Files/mydb
Далее осталось(при последующих запусках нужен только этот шаг) запустить приложение-в корне модуля вызвать mvn exec:java



В результате выполнения работы познакомился с некоторыми полезными типами Spring-Jdbc - JdbcTemplate, DataSource, с типами-операциями-
SqlUpdate, BatchSqlUpdate, ResulrSetExtractor и нкоторыми другими. Более подробно узнал о TransactionManager и одной из его реализаций
DataSourceTransactionManager. Столкнулся с вопросом на каком слое лучше осуществлять транзакции, понял что лучше это делать
на слое сервисов, а слой DAO желательно должен быть простым. Также по ходу выполнения работы возникла необходимость считывать
properties файлы при инициализации бинов Spring, справился с помощью класса org.springframework.context.support.PropertySourcesPlaceholderConfigurer.
Помимо Spring-Jdbc, впервые попробовал платформу JavaFX, написав слой View.

Немного скринов. Вот пример поиска рецепта по названию:

![Image alt](https://github.com/jjjCoder/Photos/raw/master/RecipesPhoto/Поиск по имени.png)



Создание рецепта:

![Image alt](https://github.com/jjjCoder/Photos/raw/master/RecipesPhoto/Создание рецепта.png)


Допустим пользователь случайно попытался создать рецепт с уже имеющимся названием:

![Image alt](https://github.com/jjjCoder/Photos/raw/master/RecipesPhoto/Попытка повторения.png)


Можно просмотреть список всех ингредиентов в базе:

![Image alt](https://github.com/jjjCoder/Photos/raw/master/RecipesPhoto/Список всех ингридиентов.png)


Удалить ингредиент, на который ссылаются блюда не получится:

![Image alt](https://github.com/jjjCoder/Photos/raw/master/RecipesPhoto/Попытка удаления.png)


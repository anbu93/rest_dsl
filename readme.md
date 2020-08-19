# REST DSL


Протокол работы с сервисами:

	game> form request object
	game> request object to json
	game> send http request
	server> receive request
	server> create request object from json
	server> process request
	server> create response object
	server> response object to json
	server> send http response
	game> receive response
	game> create response object from json
	game> process response

Плюсы:
- Абстрагирование от sparkjava на серверной стороне
- Абстрагирование от HttpRequest на клиентской стороне
- Единый протокол общения через JSON
- Удобный DSL для генерации необходимых классов

Минусы:
- Медленная работа из-за сериализации-десериализации в/из json
- Необходимость вручную импортировать классы передачи данных




## Пример использования

#### 1. Подготовка файлов для генерации

DSL-файл `example.rest_dsl` для генерации необходимых файлов
```
dto package "com.vova_cons.rest_dsl.test";

server Test {
    ip = "localhost";
    service Example {
        port 8081;
        https {
            keystore "keystore.jks";
            password "password"; // надо добавить чтение пароля с файла а не напрямую с кода		    
        }
        route test {
            method GET;
            request TestRequest;
            response TestResponse;
        }
}
}
```

Классы DTO созданные вручную
```java
class TestRequest {
	public long uid;
	public String name;
	public int[] args;
}

class TestResponse {
	public boolean isSuccess;
	public String answer;
	public String errorMessage;
}
```


#### 2. Генерация файлов с помощью генератора
```java
class GeneratorTest {
    public static void main(String []args) {
        RestDsl.generate("example.rest_dsl", "output/");
    }
}
```

Структура файлов в директории output/:
```
output/
    dto/
        TestRequest.java
        TestResponce.java
    Test/
        server/
            TestService.java
        client/
            TestService.java
```

Сгенерированные файлы для серверной части
```java
abstract class TestService extends RestDslService {
    public TestService() {
        super(8081);
    }
    
    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getKeystoreFile() {
        return "keystore.jks";
    }

    @Override
    public String getKeystorePassword() {
        return "password";
    }
    
	@SparkRoute(route = "/test", method = HttpMethod.GET, https=false)
	public abstract TestResponse test(TestRequest request);
}
```

Сгенерированные файлы для клиентской части
```java
class TestService extends RestServerWorker {
	public String getUrl() {
		return "https://localhost:8081";
	}

	public void test(TestRequest request, RequestCallback<TestResponse> callback) {
		sendRequest(request, callback, "/test", HttpMethod.GET);
	}
}
```

#### 3. Написание кода на серверной стороне

```java
class ServerMain {
	public static void main(String[] args) {
		TestService testService = new TestServiceImpl();
		RestDsl.ignite(testService);
	}
}

class TestServerImpl extends TestService {
	@Override
	public TestResponse test(TestRequest request) {
		TestResponse response = new TestResponse();
		response.isSuccess = true;
		int summ = 0;
		for(int num : request.args) {
			summ += num;
		}
		response.answer = request.uid + "> " + request.name + ": " + summ;
		return response;
	}
}
```

#### 4. Написание кода на клиентской стороне

```java
class ClientMain {
	public static void main(String[] args) {
		TestService testService = new TestService();
		testService.setHttpRequestProcessor(JavaHttpRequestProcessor()); // or libgdx implementation
		TestRequest request = new TestRequest();
		request.uid = 123456;
		request.name = "Test";
		request.args = new int[] { 1, 2, 3, 4, 5 };
		testService.test(request, (response) -> {
			if (response.isSuccess) {
				System.out.println(response.answer); // "123456> Test: 15"
			} else {
				System.out.println(response.errorMessage);
			}
		});
	}
}
```

## План работ

#### version 0.1
- Базовая логика серверной части
- Базовая логика клиентской части
- JettyHttpRequestProcessor
- Поддержка https

Выполнено 19/08/2020

#### version 0.2
- Генератор кода
- Парсер DSL

#### version 0.3
- Предопределенные HttpRequestProcessor-ы и их выбор в генераторе
- Решение проблемы с CORS (Options routes and Access-Control-Allow-Origin)
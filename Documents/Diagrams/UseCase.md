# Варианты использования
---

![UseCase](https://github.com/ViachaslauPratasenia/PhoneShop/blob/master/Images/Diagrams/useCase.jpg)

# Содержание
1 [Актёры](#1) <br>
2 [Варианты использования](#2) <br>
  2.1 [Просмотр подробной информации о продукте](#2.1) <br>
  2.2 [Добавление продуктов в корзину](#2.2) <br>
  2.3 [Изменение количества продуктов в корзине](#2.3) <br>
  2.4 [Удаление продуктов из корзины](#2.4) <br>
  2.5 [Ввод данных для оформления заказа](#2.5) <br>
  2.6 [Оформление заказа](#2.6) <br>

<a name="1"/>

# 1 Актёры

| Актёр | Описание |
|:--|:--|
| Пользователь | Пользователь, не зарегистрированный в системе |
<a name="2"/>

# 2 Варианты использования

<a name="2.1"/>

## 2.1 Просмотр подробной информации о продукте

**Описание.** Вариант использования "Просмотр подробной информации о продукте" позволяет пользователю просмотреть выбранный им продукт.

**Основной поток.**
1. Приложение отображает пользователю страницу со всеми продуктами;
2. Пользователь выбирает понравившийся ему продукт;
3. Приложение ищет в базе данных продукт с этим ID;
Если он отсутствует, то выполняется альтернативный поток A1;
4. Приложение получает подробную информацию о продукте;
5. Приложение отображает подробную информацию о продукте;
6. Вариант использования завершается;

**Альтернативный поток А1.**
1. Приложение выводит сообщение о том, что данный продукт не существует;
2. Возврат к п.1 основного потока;

<a name="2.2"/>

## 2.2 Добавление продуктов в корзину

**Описание.** Вариант использования "Добавление продуктов в корзину" позволяет пользователю добавить выбранный продукт в корзину.

**Основной поток.**
1. Пользователь заполняет поле количества продуктов;
2. Пользователь нажимает кнопку "Add to cart";
3. Пользователь подтверждает введенные данные, нажав кнопку "Войти".
Если введенные данные не являются числом, выполняется альтернативный поток А2
Если введенное число больше, чем количество доступных продуктов, выполняется альтернативный поток А3.
4. Получение корзины из сессии;
5. Приложение добавляет продукт в корзину;
6. Приложение сообщает пользователю об успешном добавлении;
7. Вариант использования завершается;

**Альтернативный поток А2.**

1. Приложение выводит сообщение о том, что введенные данные не являются числом;
2. Возврат к п.1 основного потока;

**Альтернативный поток А3.**
1. Приложение выводит сообщение о том, что введенные данные не являются числом
2. Возврат к п.1 основного потока; 

<a name="2.3"/>

## 2.3 Изменение количества продуктов в корзине

**Описание.** Вариант использования "Изменение количества продуктов в корзине" позволяет пользователю изменить количество продуктов в корзине.

**Основной поток.**
1. Пользователь вводит новое количество продуктов;
2. Пользователь нажимает кнопку "Update";
Если введенные данные не являются числом, выполняется альтернативный поток А2
Если введенное число больше, чем количество доступных продуктов, выполняется альтернативный поток А3.
Если введенное число меньше нуля, выполняется альтернативный поток А4;
3. Приложение увеличивает количество продуктов в корзине;
4. Приложение обновляет корзину;
5. Вариант использования завершается;

**Альтернативный поток А4.**
1. Приложение выводит сообщение о том, что введенное число меньше нуля;
2. Возврат к п.1 основного потока; 

<a name="2.4"/>

## 2.4 Удаление продуктов из корзины

**Описание.** Вариант использования "Удаление продуктов из корзины" позволяет пользователю удалить продукт из корзины.

**Основной поток.**
1. Пользователь нажимает кнопку "Delete".
2. Приложение находит продукт в базе данных.
3. Приложение удаляет продукт из базы данных.
4. Приложение обновляет корзину.
5. Вариант использования завершается.

<a name="2.5"/>

## 2.5 Ввод данных для оформления заказа
**Описание.** Вариант использования "Ввод данных для оформления заказа" позволяет пользователю ввести данные для оформления заказа.

**Основной поток.**
1. Пользователь вводит имя и фамилию.
2. Пользователь вводит адрес.
3. Пользователь вводит свой телефон.
Если какое-либо поле отсутствует, выполняется альтернативный поток А5.
4. Приложение дожидается нажатия кнопки "Make order".
5. Вариант использования завершается.

**Альтернативный поток А5.**
1. Приложение выводит сообщение о том, что не все поля были заполнены.
2. Вариант использования завершается.

## 2.6 Оформление заказа

**Описание.** Вариант использования "Оформление заказа" позволяет пользователю получить информацию о своем заказе.

**Основной поток.**
1. Приложение получает из параметров страницы данные пользователя.
2. Приложение подсчитывает общую сумму заказа.
3. Приложение отображает пользователю подробное описание заказа. 
4. Приложение очищает корзину.
5. Вариант использования завершается.

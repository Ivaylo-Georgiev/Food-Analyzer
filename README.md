# Food Analyzer

В наши дни е особено важно да знаем какво ядем и да следим калориите, които консумираме. 

Food Analyzer e многонишково клиент-сървър приложение, което ни информира за състава и енергийната стойност на хранителните продукти в менюто ни.

Един от най-изчерпателните и достоверни източници на подобна информация е [базата данни за състава на храните](https://ndb.nal.usda.gov/ndb/) на [U.S. Department of Agriculture](https://www.usda.gov/). Tази информацията е достъпна и чрез публично безплатно REST API, което е документирано [тук](https://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORTV2.md).

## Food Analyzer Server

-   Сървърът може да обслужва множество клиенти едновременно.
-   Сървърът получава команди от клиентите и връща подходящ резултат.
-   Сървърът извлича необходимите му данни от гореспоменатото _RESTful API_ и запазва (кешира) резултата в локалната си файлова система.
    
    Например, при получаване на командата `get-food raffaello`, сървърът прави следната _HTTP GET_ заявка: [https://api.nal.usda.gov/ndb/search/?q=raffaello&api_key=DEMO_KEY](https://api.nal.usda.gov/ndb/search/?q=raffaello&api_key=DEMO_KEY) и получава _HTTP response_ със статус код _200_ и с тяло следния _JSON_:



<pre><code>{
  &quot;list&quot;: {
    &quot;q&quot;: &quot;raffaello&quot;,
    &quot;sr&quot;: &quot;1&quot;,
    &quot;ds&quot;: &quot;any&quot;,
    &quot;start&quot;: 0,
    &quot;end&quot;: 1,
    &quot;total&quot;: 1,
    &quot;group&quot;: &quot;&quot;,
    &quot;sort&quot;: &quot;r&quot;,
    &quot;item&quot;: [
      {
        &quot;offset&quot;: 0,
        &quot;group&quot;: &quot;Branded Food Products Database&quot;,
        &quot;name&quot;: &quot;RAFFAELLO, ALMOND COCONUT TREAT, UPC: 009800146130&quot;,
        &quot;ndbno&quot;: &quot;45142036&quot;,
        &quot;ds&quot;: &quot;LI&quot;,
        &quot;manu&quot;: &quot;Ferrero U.S.A., Incorporated&quot;
      }
    ]
  }
}</code></pre>

Заявките към REST API-то изискват автентикация с API key.

От данните за продукта, ни интересува пълното му име (`RAFFAELLO, ALMOND COCONUT TREAT`) и уникалния му номер в базата, ndbno (`45142036`). Някои продукти, по-точно тези с група `Branded Food Products Database`, имат също и производител (`Ferrero U.S.A., Incorporated`) и UPC код (`009800146130`), който се съдържа в елемента `name`.

**Бележка:** UPC, или [Universal Product Code](https://en.wikipedia.org/wiki/Universal_Product_Code), е доминиращият в САЩ стандарт за баркод. С други думи, UPC кодът е числото, кодирано в баркода на опаковката на продуктите.

Сървърът кешира получената информация на локалната файлова система. При получаване на заявка, сървърът първо проверява дали в кеша вече съществува информация за дадения продукт, и ако е така, директно връщя тази информация, вместо да направи нова заявка към REST API-то.

## Food Analyzer Client
Клиентът осъществява връзка с _Food Analyzer Server_ на определен порт, чете команди от стандартния вход, изпраща ги към сървъра и извежда получения резултат на стандартния изход в human-readable формат. Клиентът може да изпълнява следните команди:

-   `get-food <food_name>` - извежда информацията, описана по-горе, за даден хранителен продукт. Ако сървърът върне множество продукти с даденото име, се извежда информация за всеки от тях. Ако пък липсва информация за продукта, се извежда подходящо съобщение.
-   `get-food-report <food_ndbno>` - по даден уникален номер на продукт (ndbno) извежда име на продукта, съставки (ingedients), енергийна стойност (калории), съдържание на белтъчини, мазнини, въглехидрати и фибри.
-   `get-food-by-barcode --upc=<upc_code>|--img=<barcode_image_file>` - извежда информация за продукт по неговия баркод, _ако такава е налична в кеша на сървъра_ (REST API-то не поддържа търсене на продукт по UPC код или баркод изображение!). Задължително е да се подаде един от двата параметъра: или UPC code, или баркод снимка (като пълен път и име на файла на локалната файлова система на клиента). Ако са указани и двата параметъра, img параметърът се игнорира.

Търсенето по баркод изображение, използва open source Java библиотеката [ZXing "Zebra Crossing"](https://github.com/zxing/zxing).

### Пример за валидни входни данни
```
get-food butter
get-food-report 45142036
get-food-by-barcode --img=D:\User\Photos\BarcodeImage.jpg --upc=03600029142
```

# 🍏 Food Analyzer

Nowadays it is especially important to know what we eat and to keep track of the calories we consume.

Food Analyzer is a multi-threaded client-server application, which informs us about the ingredients, calories and products in our menu.

One of the fullest and most detailed resources for such information is [the food database](https://ndb.nal.usda.gov/ndb/) of [U.S. Department of Agriculture](https://www.usda.gov/). This information is publicly accessible via the free REST API, which is documented [here](https://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORTV2.md).

## Food Analyzer Server

-   The server can manage multiple clients simultaneously.
-   The server receives commands from the clients and returns the result.
-   The server fetches its data vie the _RESTful API_ and caches the result in its local file system.
    
    For example, when the server receives the command `get-food raffaello`, it makes the following _HTTP GET_ request:[https://api.nal.usda.gov/ndb/search/?q=raffaello&api_key=DEMO_KEY](https://api.nal.usda.gov/ndb/search/?q=raffaello&api_key=DEMO_KEY) and receives _HTTP response_ with status code _200_ and body the following _JSON_:



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

The requests to the REST API require authentication with an API key.  

From the data about the product, we are interested in its full name (`RAFFAELLO, ALMOND COCONUT TREAT`) and its unique number in the database, ndbno (`45142036`). Some products, specifically those with group `Branded Food Products Database`, also contain a producer (`Ferrero U.S.A., Incorporated`) and a UPC code (`009800146130`) in the element `name`.  

**📓 Note:** UPC, or [Universal Product Code](https://en.wikipedia.org/wiki/Universal_Product_Code), is the dominant barcode standart in the USA. In other words, the UPC code is the number, encoded in the barcode of the package.  

The server caches the received information in its file system. When it receives a request, it first the cache for information about the product. If the data is in the cache, it is directly returned to the server, instead of making a new request to the REST API.  

## Food Analyzer Client
The client connects to the _Food Analyzer Server_ at a specified port, reads command from the standart input, forwards them to the server and prints the result to the standart output in human-readable format. The client can execute the following commands:  

-   `get-food <food_name>` - prints the information, described above for a particular product. If the server returns a set of products with a name, information is displayed for each of them. If there is no information for the product, an appropriate message is displayed. 
-   `get-food-report <food_ndbno>` - by a unique number of a product (ndbno), prints the name of the product, ingredients, calories, proteins, fats, carbohydrates and fibres.  
-   `get-food-by-barcode --upc=<upc_code>|--img=<barcode_image_file>` - prints data about a product by a barcode, _if there is such in the cache_ (the REST API does not support searching for a product by a UPC code or barcode image!). It is required to pass one of the two parameters (UPC code or a barcode image as a full path to the file in the client's local file system). If both parameters are provided, the img parameter is ignored.  

Searching by a barcod image uses the open source Java library [ZXing "Zebra Crossing"](https://github.com/zxing/zxing).

### Example of valid input
```
get-food butter
get-food-report 45142036
get-food-by-barcode --img=D:\User\Photos\BarcodeImage.jpg --upc=03600029142
```

# PMML in Java

This is how you can train and save a Machine Learning model in R and then load it in Java to make predictions.

The demo is based on a PMML model of the classical Iris data set trained by the following R 3.6 script
using a decision tree:

```R
library("rpart")
library("rpart.plot")
library("XML")
library("pmml")

# Uncomment the following line specifying your custom local path following the conventions of your OS:
# setwd("/my/output/directory/path/")

data("iris")

tree <- rpart(Species ~ ., data = iris, method = "class")
rpart.plot(tree)
saveXML(pmml(tree), "rpart.pmml")
```

The PMML file was saved in pmml-demo/src/main/resources/models/rpart.pmml

### Pre-requisites:
- Java 8
- Gradle 5.5

### To build it:
```bash
cd demo/src
./gradlew clean build
```

### To run the application:
```bash
cd demo/src
./gradlew bootRun
```

or:

```bash
cd demo/build/libs
java -jar demo-0.0.1-SNAPSHOT.jar
```

### To predict:

http://localhost:8080/predict?petalLength=2.6&petalWidth=1.7&sepalLength=0.5&sepalWidth=1.9

Expected output:

```
{
  "Predicted_Species": "versicolor",
  "Probability_setosa": "0.0",
  "Probability_versicolor": "0.9074074074074074",
  "Probability_virginica": "0.09259259259259259"
}
```

package com.davide.demo2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    private final Handler handler;

    @Autowired
    public Controller(final Handler handler) {
        this.handler = handler;
    }

    @RequestMapping(value = "/predict", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> predict(@Valid @RequestParam final Double petalLength,
                                       @Valid @RequestParam final Double petalWidth,
                                       @Valid @RequestParam final Double sepalLength,
                                       @Valid @RequestParam final Double sepalWidth) {

        // N.B.: the names of the predictors must match the names in the PMML model
        final Map<String, Double> predictors = new HashMap<>();
        predictors.put("Petal.Length", petalLength);
        predictors.put("Petal.Width", petalWidth);
        predictors.put("Sepal.Length", sepalLength);
        predictors.put("Sepal.Width", sepalWidth);

        predictors.forEach((key, value) -> LOG.info(key + " " + value));

        return handler.predict(predictors);
    }
}

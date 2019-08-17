package com.davide.demo2;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.ModelField;
import org.jpmml.evaluator.OutputField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.davide.demo2.ModelLoader.evaluator;

@Service
class Handler {

    private static final Logger LOG = LoggerFactory.getLogger(Handler.class);

    Map<String, String> predict(final Map<String, Double> predictors) {

        final Map<String, String> predictions = new TreeMap<>();

        // Printing input (x1, x2, .., xn) fields
        final List<? extends InputField> inputFields = evaluator.getInputFields();
        LOG.info("Input fields: " + inputFields);

        final Map<FieldName, FieldValue> inputMap = inputFields
                .stream()
                .peek(System.out::println)
                .collect(Collectors.toMap(
                        ModelField::getFieldName,
                        $ -> $.prepare(predictors.get($.getName().toString()))));

        inputMap.entrySet().forEach(System.out::println);

        final Map<FieldName, ?> results = evaluator.evaluate(inputMap);

        results.forEach((key, value) -> LOG.info(key + " " + value));

        // Printing secondary result (eg. probability(y), decision(y)) fields
        final List<OutputField> outputFields = evaluator.getOutputFields();

        LOG.info("Output fields: " + outputFields);

        for (final OutputField outputField : outputFields) {
            final FieldName fieldName = outputField.getName();
            final String key = fieldName.getValue();
            final String value = results.get(fieldName).toString();
            predictions.put(key, value);
        }

        return predictions;
    }
}

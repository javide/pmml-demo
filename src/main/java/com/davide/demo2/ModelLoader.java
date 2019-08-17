package com.davide.demo2;

import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;

class ModelLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ModelLoader.class);

    final static Evaluator evaluator = loadModel("/models/rpart.pmml");

    private static Evaluator loadModel(String filename) {

        Evaluator evaluator = null;

        LOG.info("Loading model from " + filename);
        try {
            evaluator = new LoadingModelEvaluatorBuilder()
                    .setLocatable(false)
                    .setVisitors(new DefaultVisitorBattery())
                    .load(ModelLoader.class.getResourceAsStream(filename))
                    .build();

            // Performing the self-check
            evaluator.verify();

        } catch (SAXException | JAXBException e) {
            LOG.error("Exiting as cannot load model file " + filename, e);
            System.exit(1);
        }

        return evaluator;
    }
}

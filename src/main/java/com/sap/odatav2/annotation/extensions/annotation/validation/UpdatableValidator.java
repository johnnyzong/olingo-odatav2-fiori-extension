package com.sap.odatav2.annotation.extensions.annotation.validation;

import com.sap.cloud.commons.collection.CollectionUtils;
import com.sap.cloud.commons.lambda.ThrowingFunction;
import com.sap.odatav2.annotation.extensions.annotation.util.ValidatorUtils;
import com.sap.odatav2.annotation.extensions.annotation.validation.base.ValidatorParameters;
import com.sap.odatav2.annotation.extensions.annotation.value.AnnotationPropertyNames;
import com.sap.odatav2.annotation.extensions.annotation.validation.base.AnnotationValidator;
import org.apache.olingo.odata2.annotation.processor.api.extension.exception.ODataValidationFailedException;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.uri.expression.ExceptionVisitExpression;

public class UpdatableValidator implements AnnotationValidator {
    public UpdatableValidator() {
    }

    public void operationValidation(ValidatorParameters params) throws ODataValidationFailedException, ExceptionVisitExpression, ODataApplicationException, EdmException {
        if(this.requestMethodIsRelevantForFieldValidation(params.getODataHttpMethod()) && CollectionUtils.allNonNull(new Object[]{params.getEdmEntitySet(), params.getRequestParams()})) {
            try {
                ValidatorUtils.checkAnnotationEntitySet(params.getEdmEntitySet(), AnnotationPropertyNames.Updatable.getPropertyName());
                ValidatorUtils.checkEntityPropertyAnnotations(params.getEdmEntitySet(), params.getRequestParams(), AnnotationPropertyNames.Updatable);
            } catch (EdmException var3) {
                ThrowingFunction.throwManagedException(var3);
            }
        }

    }

    public boolean requestMethodIsRelevantForFieldValidation(ODataHttpMethod odataHttpMethod) {
        return odataHttpMethod == ODataHttpMethod.MERGE || odataHttpMethod == ODataHttpMethod.PATCH || odataHttpMethod == ODataHttpMethod.PUT;
    }
}
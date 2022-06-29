
package com.prashant.credit.data.provider;

import static org.junit.jupiter.params.provider.Arguments.*;

import com.prashant.credit.data.factory.SimulationDataFactory;
import com.prashant.credit.model.Simulation;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class SimulationDataProvider {

    private SimulationDataProvider() {
    }

    public static Stream<Arguments> failedValidations() {
        SimulationDataFactory simulationDataFactory = new SimulationDataFactory();

        Simulation simulationLessThanMinAmount = simulationDataFactory.simulationLessThanMinAmount();
        Simulation simulationExceedAmount = simulationDataFactory.simulationExceedAmount();
        Simulation simulationLessThanMinInstallments = simulationDataFactory.simulationLessThanMinInstallments();
        Simulation simulationExceedInstallments = simulationDataFactory.simulationExceedInstallments();
        Simulation simulationWithNotValidEmail = simulationDataFactory.simulationWithNotValidEmail();
        Simulation simulationWithEmptyCPF = simulationDataFactory.simulationWithEmptyCPF();
        Simulation simulationWithEmptyName = simulationDataFactory.simulationWithEmptyName();

        return Stream.of(
            arguments(simulationLessThanMinAmount, "errors.amount", "Amount must be equal or greater than $ 1.000"),
            arguments(simulationExceedAmount, "errors.amount", "Amount must be equal or less than than $ 40.000"),
            arguments(simulationLessThanMinInstallments, "errors.installments", "Installments must be equal or greater than 2"),
            arguments(simulationExceedInstallments, "errors.installments", "Installments must be equal or less than 48"),
            arguments(simulationWithNotValidEmail, "errors.email", "must be a well-formed email address"),
            arguments(simulationWithEmptyCPF, "errors.cpf", "CPF cannot be empty"),
            arguments(simulationWithEmptyName, "errors.name", "Name cannot be empty")
        );
    }
}

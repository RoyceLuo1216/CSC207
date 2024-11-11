package usecase.flexible;

import com.google.ortools.Loader;
import com.google.ortools.sat.*;
import EventEntity.FlexibleEvent;

import java.util.List;

/**
 * Optimizes the scheduling of flexible events.
 */
public class FlexibleEventOptimizer {

    public void optimizeFlexibleEvents(List<FlexibleEvent> events) {
        Loader.loadNativeLibraries();
        CpModel model = new CpModel();

        for (FlexibleEvent event : events) {
            IntVar startTime = model.newIntVar(event.getDayStart().getHour(), event.getDayEnd().getHour(), event.getEventName());

            // Add constraints based on priority and time allocation
            model.addLessOrEqual(startTime, event.getDayEnd().getHour());
            model.addGreaterOrEqual(startTime, event.getDayStart().getHour());

            // Additional constraints for time allocation
        }

        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);

        if (status == CpSolverStatus.OPTIMAL) {
            for (FlexibleEvent event : events) {
                System.out.println(event.getEventName() + " scheduled at " + solver.value(model.newIntVar(event.getDayStart().getHour(), event.getDayEnd().getHour(), event.getEventName())));
            }
        } else {
            System.out.println("No feasible schedule found.");
        }
    }
}

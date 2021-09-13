package com.crg.learning.controller.test.support;

import com.crg.learn.usecase.concept.UseCase;
import org.mockito.invocation.InvocationOnMock;

import java.util.function.BiConsumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public final class UseCaseMocking {
    private UseCaseMocking() {
        // default
    }

    public static <I, O> void prepare(UseCase<I, O> useCase, BiConsumer<I, O> actionToPerform) {
        doAnswer(useCaseInvocation -> {
            I request = requestFrom(useCaseInvocation);
            O responder = responderFrom(useCaseInvocation);

            actionToPerform.accept(request, responder);
            return null;
        }).when(useCase).execute(any(), any());
    }

    private static <I> I requestFrom(InvocationOnMock invocation) {
        return invocation.getArgument(0);
    }

    private static <O> O responderFrom(InvocationOnMock invocation) {
        return invocation.getArgument(1);
    }
}

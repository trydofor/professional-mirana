package pro.fessional.mirana.flow;

/**
 * @author trydofor
 * @since 2021-02-19
 */
public enum FlowEnum {

    /**
     * Default action of flow
     */
    Default,
    /**
     * Continue the flow (loop)
     */
    Continue,
    /**
     * Break the flow (loop)
     */
    Break,
    /**
     * break the flow (method) and return form the method
     */
    Return,

    /**
     * break the flow (method) and throw some predefined exception
     */
    Throw
}

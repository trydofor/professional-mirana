package pro.fessional.mirana.cast;


import org.jetbrains.annotations.Contract;

/**
 * syntax cast sugar for subclass and super method chaining
 *
 * @author trydofor
 * @since 2025-01-09
 */
public interface ChainingCast {

    /**
     * <pre><code>
     *  // (1) Bottom-Up style
     *  new SubClass()
     *  .subXxx1() // sub method
     *  .superZzz1() // super method
     *  .cast(); // finally cast
     *
     *  // (2) Top-Down style
     *  new SubClass()
     *  .superZzz1() // super method
     *  .&lt;SubClass&gt;cast(); // middle cast
     *  .subXxx1() // sub method
     *  </code></pre>
     */
    @Contract("->this")
    @SuppressWarnings("unchecked")
    default <S extends ChainingCast> S cast() {
        return (S) this;
    }
}

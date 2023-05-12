import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionTypeTest
{
    @Test
    public void isMethods()
    {
        Assertions.assertTrue(SessionType.TALK.isTalk());
        Assertions.assertFalse(SessionType.TALK.isWorkshop());
        Assertions.assertTrue(SessionType.WORKSHOP.isWorkshop());
        Assertions.assertFalse(SessionType.WORKSHOP.isTalk());
    }
}

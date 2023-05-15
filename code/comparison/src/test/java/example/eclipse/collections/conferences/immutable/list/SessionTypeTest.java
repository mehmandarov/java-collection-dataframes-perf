package example.eclipse.collections.conferences.immutable.list;

import example.eclipse.collections.conferences.immutable.set.SessionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionTypeTest
{
    @Test
    public void isMethods()
    {
        Assertions.assertTrue(example.eclipse.collections.conferences.immutable.set.SessionType.TALK.isTalk());
        Assertions.assertFalse(example.eclipse.collections.conferences.immutable.set.SessionType.TALK.isWorkshop());
        Assertions.assertTrue(example.eclipse.collections.conferences.immutable.set.SessionType.WORKSHOP.isWorkshop());
        Assertions.assertFalse(SessionType.WORKSHOP.isTalk());
    }
}

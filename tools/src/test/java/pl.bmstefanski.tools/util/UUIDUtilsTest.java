package pl.bmstefanski.tools.util;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UUIDUtilsTest {

    @Test
    public void shouldHas16BytesFromUniqueId() {
        UUID uniqueId = UUID.randomUUID();
        byte[] bytes = UUIDUtils.getBytesFromUUID(uniqueId);

        assertEquals(16, bytes.length);
    }

    @Test
    public void shouldReconstructSameUniqueIdFromBytes() {
        UUID uniqueId = UUID.randomUUID();
        byte[] bytes = UUIDUtils.getBytesFromUUID(uniqueId);
        UUID newUniqueId = UUIDUtils.getUUIDFromBytes(bytes);

        assertEquals(uniqueId, newUniqueId);
    }

    @Test
    public void shouldGenerateSameUniqueIdFromBytesWhenItIsFromString() {
        UUID uniqueId = UUID.fromString("02cea57b-a0fb-4391-985e-89e76d642240");
        byte[] bytes = UUIDUtils.getBytesFromUUID(uniqueId);
        UUID newUniqueId = UUIDUtils.getUUIDFromBytes(bytes);

        assertEquals(uniqueId, newUniqueId);
    }

}

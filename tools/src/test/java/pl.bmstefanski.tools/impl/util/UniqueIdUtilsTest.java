package pl.bmstefanski.tools.impl.util;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UniqueIdUtilsTest {

  @Test
  public void shouldHas16BytesFromUniqueId() {
    UUID uniqueId = UUID.randomUUID();
    byte[] bytes = UniqueIdUtils.getBytesFromUUID(uniqueId);

    assertEquals(16, bytes.length);
  }

  @Test
  public void shouldReconstructSameUniqueIdFromBytes() {
    UUID uniqueId = UUID.randomUUID();
    byte[] bytes = UniqueIdUtils.getBytesFromUUID(uniqueId);
    UUID newUniqueId = UniqueIdUtils.getUUIDFromBytes(bytes);

    assertEquals(uniqueId, newUniqueId);
  }

  @Test
  public void shouldGenerateSameUniqueIdFromBytesWhenItIsFromString() {
    UUID uniqueId = UUID.fromString("02cea57b-a0fb-4391-985e-89e76d642240");
    byte[] bytes = UniqueIdUtils.getBytesFromUUID(uniqueId);
    UUID newUniqueId = UniqueIdUtils.getUUIDFromBytes(bytes);

    assertEquals(uniqueId, newUniqueId);
  }

}

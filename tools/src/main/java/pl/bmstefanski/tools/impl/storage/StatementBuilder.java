package pl.bmstefanski.tools.impl.storage;

import pl.bmstefanski.tools.impl.util.Builder;

public final class StatementBuilder implements Builder<String> {

  private final StringBuilder stringBuilder = new StringBuilder();

  public StatementBuilder createTableIfDoesNotExist(String tableName, String tableArguments) {
    this.stringBuilder.append("CREATE TABLE IF NOT EXISTS `").append(tableName).append("` (").append(tableArguments).append(")");
    return this;
  }

  public StatementBuilder insertInto(String tableName) {
    this.stringBuilder.append("INSERT INTO `").append(tableName).append("` ");
    return this;
  }

  public StatementBuilder values(String values) {
    this.stringBuilder.append("VALUES (").append(values).append(") ");
    return this;
  }

  public StatementBuilder onDuplicateKey() {
    this.stringBuilder.append("ON DUPLICATE KEY ");
    return this;
  }

  public StatementBuilder update(String valuesToUpdate) {
    this.stringBuilder.append("UPDATE").append(valuesToUpdate).append(" ");
    return this;
  }

  public StatementBuilder select() {
    this.stringBuilder.append("SELECT ");
    return this;
  }

  public StatementBuilder all() {
    this.stringBuilder.append("* ");
    return this;
  }

  public StatementBuilder where(String value) {
    this.stringBuilder.append("WHERE `").append(value).append("` ");
    return this;
  }

  public StatementBuilder from(String tableName) {
    this.stringBuilder.append("FROM `").append(tableName).append("` ");
    return this;
  }

  public StatementBuilder delete() {
    this.stringBuilder.append("DELETE ");
    return this;
  }

  @Override
  public String build() {
    return stringBuilder.toString();
  }

}

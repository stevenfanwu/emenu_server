/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cn.com.cloudstone.menu.server.thrift.api;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单
 */
public class Order implements org.apache.thrift.TBase<Order, Order._Fields>, java.io.Serializable, Cloneable, Comparable<Order> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Order");

  private static final org.apache.thrift.protocol.TField GOODS_FIELD_DESC = new org.apache.thrift.protocol.TField("goods", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField ORIGINAL_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("originalPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("price", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField REMARKS_FIELD_DESC = new org.apache.thrift.protocol.TField("remarks", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TABLE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tableId", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OrderStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OrderTupleSchemeFactory());
  }

  public List<GoodsOrder> goods; // required
  public double originalPrice; // required
  public double price; // required
  public String remarks; // required
  public String tableId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GOODS((short)1, "goods"),
    ORIGINAL_PRICE((short)2, "originalPrice"),
    PRICE((short)3, "price"),
    REMARKS((short)4, "remarks"),
    TABLE_ID((short)5, "tableId");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // GOODS
          return GOODS;
        case 2: // ORIGINAL_PRICE
          return ORIGINAL_PRICE;
        case 3: // PRICE
          return PRICE;
        case 4: // REMARKS
          return REMARKS;
        case 5: // TABLE_ID
          return TABLE_ID;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ORIGINALPRICE_ISSET_ID = 0;
  private static final int __PRICE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GOODS, new org.apache.thrift.meta_data.FieldMetaData("goods", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, GoodsOrder.class))));
    tmpMap.put(_Fields.ORIGINAL_PRICE, new org.apache.thrift.meta_data.FieldMetaData("originalPrice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.PRICE, new org.apache.thrift.meta_data.FieldMetaData("price", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.REMARKS, new org.apache.thrift.meta_data.FieldMetaData("remarks", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE_ID, new org.apache.thrift.meta_data.FieldMetaData("tableId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Order.class, metaDataMap);
  }

  public Order() {
  }

  public Order(
    List<GoodsOrder> goods,
    double originalPrice,
    double price,
    String remarks,
    String tableId)
  {
    this();
    this.goods = goods;
    this.originalPrice = originalPrice;
    setOriginalPriceIsSet(true);
    this.price = price;
    setPriceIsSet(true);
    this.remarks = remarks;
    this.tableId = tableId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Order(Order other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetGoods()) {
      List<GoodsOrder> __this__goods = new ArrayList<GoodsOrder>(other.goods.size());
      for (GoodsOrder other_element : other.goods) {
        __this__goods.add(new GoodsOrder(other_element));
      }
      this.goods = __this__goods;
    }
    this.originalPrice = other.originalPrice;
    this.price = other.price;
    if (other.isSetRemarks()) {
      this.remarks = other.remarks;
    }
    if (other.isSetTableId()) {
      this.tableId = other.tableId;
    }
  }

  public Order deepCopy() {
    return new Order(this);
  }

  @Override
  public void clear() {
    this.goods = null;
    setOriginalPriceIsSet(false);
    this.originalPrice = 0.0;
    setPriceIsSet(false);
    this.price = 0.0;
    this.remarks = null;
    this.tableId = null;
  }

  public int getGoodsSize() {
    return (this.goods == null) ? 0 : this.goods.size();
  }

  public java.util.Iterator<GoodsOrder> getGoodsIterator() {
    return (this.goods == null) ? null : this.goods.iterator();
  }

  public void addToGoods(GoodsOrder elem) {
    if (this.goods == null) {
      this.goods = new ArrayList<GoodsOrder>();
    }
    this.goods.add(elem);
  }

  public List<GoodsOrder> getGoods() {
    return this.goods;
  }

  public Order setGoods(List<GoodsOrder> goods) {
    this.goods = goods;
    return this;
  }

  public void unsetGoods() {
    this.goods = null;
  }

  /** Returns true if field goods is set (has been assigned a value) and false otherwise */
  public boolean isSetGoods() {
    return this.goods != null;
  }

  public void setGoodsIsSet(boolean value) {
    if (!value) {
      this.goods = null;
    }
  }

  public double getOriginalPrice() {
    return this.originalPrice;
  }

  public Order setOriginalPrice(double originalPrice) {
    this.originalPrice = originalPrice;
    setOriginalPriceIsSet(true);
    return this;
  }

  public void unsetOriginalPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORIGINALPRICE_ISSET_ID);
  }

  /** Returns true if field originalPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetOriginalPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __ORIGINALPRICE_ISSET_ID);
  }

  public void setOriginalPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORIGINALPRICE_ISSET_ID, value);
  }

  public double getPrice() {
    return this.price;
  }

  public Order setPrice(double price) {
    this.price = price;
    setPriceIsSet(true);
    return this;
  }

  public void unsetPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  /** Returns true if field price is set (has been assigned a value) and false otherwise */
  public boolean isSetPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  public void setPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRICE_ISSET_ID, value);
  }

  public String getRemarks() {
    return this.remarks;
  }

  public Order setRemarks(String remarks) {
    this.remarks = remarks;
    return this;
  }

  public void unsetRemarks() {
    this.remarks = null;
  }

  /** Returns true if field remarks is set (has been assigned a value) and false otherwise */
  public boolean isSetRemarks() {
    return this.remarks != null;
  }

  public void setRemarksIsSet(boolean value) {
    if (!value) {
      this.remarks = null;
    }
  }

  public String getTableId() {
    return this.tableId;
  }

  public Order setTableId(String tableId) {
    this.tableId = tableId;
    return this;
  }

  public void unsetTableId() {
    this.tableId = null;
  }

  /** Returns true if field tableId is set (has been assigned a value) and false otherwise */
  public boolean isSetTableId() {
    return this.tableId != null;
  }

  public void setTableIdIsSet(boolean value) {
    if (!value) {
      this.tableId = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GOODS:
      if (value == null) {
        unsetGoods();
      } else {
        setGoods((List<GoodsOrder>)value);
      }
      break;

    case ORIGINAL_PRICE:
      if (value == null) {
        unsetOriginalPrice();
      } else {
        setOriginalPrice((Double)value);
      }
      break;

    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((Double)value);
      }
      break;

    case REMARKS:
      if (value == null) {
        unsetRemarks();
      } else {
        setRemarks((String)value);
      }
      break;

    case TABLE_ID:
      if (value == null) {
        unsetTableId();
      } else {
        setTableId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GOODS:
      return getGoods();

    case ORIGINAL_PRICE:
      return Double.valueOf(getOriginalPrice());

    case PRICE:
      return Double.valueOf(getPrice());

    case REMARKS:
      return getRemarks();

    case TABLE_ID:
      return getTableId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GOODS:
      return isSetGoods();
    case ORIGINAL_PRICE:
      return isSetOriginalPrice();
    case PRICE:
      return isSetPrice();
    case REMARKS:
      return isSetRemarks();
    case TABLE_ID:
      return isSetTableId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Order)
      return this.equals((Order)that);
    return false;
  }

  public boolean equals(Order that) {
    if (that == null)
      return false;

    boolean this_present_goods = true && this.isSetGoods();
    boolean that_present_goods = true && that.isSetGoods();
    if (this_present_goods || that_present_goods) {
      if (!(this_present_goods && that_present_goods))
        return false;
      if (!this.goods.equals(that.goods))
        return false;
    }

    boolean this_present_originalPrice = true;
    boolean that_present_originalPrice = true;
    if (this_present_originalPrice || that_present_originalPrice) {
      if (!(this_present_originalPrice && that_present_originalPrice))
        return false;
      if (this.originalPrice != that.originalPrice)
        return false;
    }

    boolean this_present_price = true;
    boolean that_present_price = true;
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (this.price != that.price)
        return false;
    }

    boolean this_present_remarks = true && this.isSetRemarks();
    boolean that_present_remarks = true && that.isSetRemarks();
    if (this_present_remarks || that_present_remarks) {
      if (!(this_present_remarks && that_present_remarks))
        return false;
      if (!this.remarks.equals(that.remarks))
        return false;
    }

    boolean this_present_tableId = true && this.isSetTableId();
    boolean that_present_tableId = true && that.isSetTableId();
    if (this_present_tableId || that_present_tableId) {
      if (!(this_present_tableId && that_present_tableId))
        return false;
      if (!this.tableId.equals(that.tableId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Order other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGoods()).compareTo(other.isSetGoods());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGoods()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.goods, other.goods);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOriginalPrice()).compareTo(other.isSetOriginalPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOriginalPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.originalPrice, other.originalPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrice()).compareTo(other.isSetPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.price, other.price);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRemarks()).compareTo(other.isSetRemarks());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRemarks()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.remarks, other.remarks);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTableId()).compareTo(other.isSetTableId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTableId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tableId, other.tableId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Order(");
    boolean first = true;

    sb.append("goods:");
    if (this.goods == null) {
      sb.append("null");
    } else {
      sb.append(this.goods);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("originalPrice:");
    sb.append(this.originalPrice);
    first = false;
    if (!first) sb.append(", ");
    sb.append("price:");
    sb.append(this.price);
    first = false;
    if (!first) sb.append(", ");
    sb.append("remarks:");
    if (this.remarks == null) {
      sb.append("null");
    } else {
      sb.append(this.remarks);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tableId:");
    if (this.tableId == null) {
      sb.append("null");
    } else {
      sb.append(this.tableId);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OrderStandardSchemeFactory implements SchemeFactory {
    public OrderStandardScheme getScheme() {
      return new OrderStandardScheme();
    }
  }

  private static class OrderStandardScheme extends StandardScheme<Order> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Order struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GOODS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.goods = new ArrayList<GoodsOrder>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  GoodsOrder _elem10;
                  _elem10 = new GoodsOrder();
                  _elem10.read(iprot);
                  struct.goods.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setGoodsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ORIGINAL_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.originalPrice = iprot.readDouble();
              struct.setOriginalPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.price = iprot.readDouble();
              struct.setPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REMARKS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.remarks = iprot.readString();
              struct.setRemarksIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TABLE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tableId = iprot.readString();
              struct.setTableIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Order struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.goods != null) {
        oprot.writeFieldBegin(GOODS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.goods.size()));
          for (GoodsOrder _iter11 : struct.goods)
          {
            _iter11.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ORIGINAL_PRICE_FIELD_DESC);
      oprot.writeDouble(struct.originalPrice);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PRICE_FIELD_DESC);
      oprot.writeDouble(struct.price);
      oprot.writeFieldEnd();
      if (struct.remarks != null) {
        oprot.writeFieldBegin(REMARKS_FIELD_DESC);
        oprot.writeString(struct.remarks);
        oprot.writeFieldEnd();
      }
      if (struct.tableId != null) {
        oprot.writeFieldBegin(TABLE_ID_FIELD_DESC);
        oprot.writeString(struct.tableId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OrderTupleSchemeFactory implements SchemeFactory {
    public OrderTupleScheme getScheme() {
      return new OrderTupleScheme();
    }
  }

  private static class OrderTupleScheme extends TupleScheme<Order> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Order struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGoods()) {
        optionals.set(0);
      }
      if (struct.isSetOriginalPrice()) {
        optionals.set(1);
      }
      if (struct.isSetPrice()) {
        optionals.set(2);
      }
      if (struct.isSetRemarks()) {
        optionals.set(3);
      }
      if (struct.isSetTableId()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetGoods()) {
        {
          oprot.writeI32(struct.goods.size());
          for (GoodsOrder _iter12 : struct.goods)
          {
            _iter12.write(oprot);
          }
        }
      }
      if (struct.isSetOriginalPrice()) {
        oprot.writeDouble(struct.originalPrice);
      }
      if (struct.isSetPrice()) {
        oprot.writeDouble(struct.price);
      }
      if (struct.isSetRemarks()) {
        oprot.writeString(struct.remarks);
      }
      if (struct.isSetTableId()) {
        oprot.writeString(struct.tableId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Order struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.goods = new ArrayList<GoodsOrder>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            GoodsOrder _elem15;
            _elem15 = new GoodsOrder();
            _elem15.read(iprot);
            struct.goods.add(_elem15);
          }
        }
        struct.setGoodsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.originalPrice = iprot.readDouble();
        struct.setOriginalPriceIsSet(true);
      }
      if (incoming.get(2)) {
        struct.price = iprot.readDouble();
        struct.setPriceIsSet(true);
      }
      if (incoming.get(3)) {
        struct.remarks = iprot.readString();
        struct.setRemarksIsSet(true);
      }
      if (incoming.get(4)) {
        struct.tableId = iprot.readString();
        struct.setTableIdIsSet(true);
      }
    }
  }

}


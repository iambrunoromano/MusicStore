export interface Shipment{
  id : number;
  shipDate : Date;
  arriveDate : Date;
  shipAddress : string;
  total : number;
  idOrder : number;
}

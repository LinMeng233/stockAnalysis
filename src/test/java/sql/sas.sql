
DROP TABLE IF EXISTS `sas`.`foreign_capital_flow_info`;
CREATE TABLE `sas`.`foreign_capital_flow_info`  (
   `transaction_date` char(8)   NOT NULL COMMENT '交易时间',
   `sh_inflow_amount` decimal(12, 4)   COMMENT '沪股通流入总额,亿元',
   `sz_inflow_amount` decimal(12, 4)   COMMENT '深股通流入总额,亿元',
  `total_inflow_amount` decimal(12, 4)   COMMENT '流入总额',
  `sh_balance` decimal(12, 4)  COMMENT '沪股通余额',
  `sz_balance` decimal(12, 4)  COMMENT '深股通余额',
  `total_balance` decimal(12, 4)  COMMENT '总余额',
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期时间',
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期时间',
  PRIMARY KEY (`transaction_date`)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS `sas`.`calculation_result_of255_trading_day`;
CREATE TABLE `sas`.`calculation_result_of255_trading_day` (
   `transaction_date` char(8)   NOT NULL COMMENT '交易时间',
   `inflow_amount` decimal(12, 4)   COMMENT '当日流入总额',
   `variance` decimal(12, 4)   COMMENT '255个交易日方差',
   `sum` decimal(12, 4)   COMMENT '255个交易日总数',
   `average` decimal(12, 4)   COMMENT '255个交易日平均数',
   `standard_deviation` decimal(12, 4)   COMMENT '255个交易日标准差',
   `calculation_result` decimal(12, 4)  COMMENT '当日流入总额-255个交易日平均数-2*255个交易日标准差',
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期时间',
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期时间',
 PRIMARY KEY (`transaction_date`)
)ENGINE=InnoDB ;


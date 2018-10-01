<template>
  <Sider hide-trigger :style="{background: '#fff'}">
    <Menu theme="light" width="auto" :open-names="openNames" :accordion="accordion" :active-name="activeName">
      <Submenu name="sys">
        <template slot="title">
          <Icon type="md-home" size="20"></Icon>
          系统管理
        </template>
        <MenuItem name="dist" :on-select="onSelect">区域管理</MenuItem>
        <MenuItem name="org" :on-select="onSelect">机构管理</MenuItem>
        <MenuItem name="emp">用户管理</MenuItem>
        <MenuItem name="role">角色管理</MenuItem>
        <MenuItem name="permission">权限管理</MenuItem>
        <MenuItem name="dic">字典管理</MenuItem>
        <MenuItem name="log">日志管理</MenuItem>
        <MenuItem name="announcement">公告管理</MenuItem>
      </Submenu>
      <Submenu name="account">
        <template slot="title">
          <Icon type="md-person" size="20"></Icon>
          账户管理
        </template>
        <MenuItem name="entry">表具入库</MenuItem>
        <MenuItem name="createArchive">用户建档</MenuItem>
        <MenuItem name="installMeter">挂表</MenuItem>
        <MenuItem name="account">开户</MenuItem>
        <MenuItem name="lockAccount">账户锁定</MenuItem>
        <MenuItem name="alter">账户变更</MenuItem>
      </Submenu>
      <Submenu name="payment">
        <template slot="title">
          <Icon type="logo-yen" size="20"></Icon>
          充值缴费管理
        </template>
        <MenuItem name="prePayment">预付费充值</MenuItem>
        <MenuItem name="replaceCard">补卡充值</MenuItem>
        <MenuItem name="postPayment">后付费充值</MenuItem>
        <MenuItem name="invoice">发票管理</MenuItem>
      </Submenu>
      <Submenu name="local_gas_station">
        <template slot="title">
          <Icon type="ios-keypad" size="20"></Icon>
          维修补气管理
        </template>
        <MenuItem name="input">维修单录入</MenuItem>
        <MenuItem name="fillGas">维修补气</MenuItem>
        <MenuItem name="balance">补缴结算</MenuItem>
        <MenuItem name="initCard">IC卡初始化</MenuItem>
      </Submenu>
      <Submenu name="finance">
        <template slot="title">
          <Icon type="md-card" size="20"></Icon>
          财务管理
        </template>
        <MenuItem name="preStrike">预冲账</MenuItem>
        <MenuItem name="strike">冲账</MenuItem>
      </Submenu>
      <Submenu name="meter">
        <template slot="title">
          <Icon type="md-speedometer" size="20"></Icon>
          表具运行管理
        </template>
        <MenuItem name="record">抄表</MenuItem>
        <MenuItem name="control">阀门控制</MenuItem>
        <MenuItem name="exception">异常情况</MenuItem>
      </Submenu>
      <Submenu name="query">
        <template slot="title">
          <Icon type="md-stats" size="20"></Icon>
          查询统计
        </template>
        <MenuItem name="record">IC卡查询</MenuItem>
        <MenuItem name="control">开户信息查询</MenuItem>
        <MenuItem name="exception">用户信息查询</MenuItem>
        <MenuItem name="exception">异常用户查询</MenuItem>
        <MenuItem name="exception">营业数据查询</MenuItem>
        <MenuItem name="exception">营业报表查询</MenuItem>
      </Submenu>
    </Menu>
  </Sider>
</template>

<script>
export default {
  name: 'side-bar'
}
</script>

<style scoped>

</style>

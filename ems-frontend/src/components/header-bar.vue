<template>
  <Row type="flex">
    <Col span="6" order="1">
      <div class="layout-logo"></div>
    </Col>
    <Col span="6" order="2">2 | order-3</Col>
    <Col span="6" order="3">3 | order-2</Col>
    <Col span="6" order="4">
      <Menu mode="horizontal" theme="primary">
        <div class="layout-nav">
          <MenuItem name="1">
            <Icon type="md-lock" size="20"></Icon>
            锁屏
          </MenuItem>
          <MenuItem name="2">
            <Icon type="md-refresh" size="20"></Icon>
            刷新
          </MenuItem>
          <MenuItem name="3">
            <Icon type="md-person" size="20"></Icon>
            用户信息
          </MenuItem>
        </div>
      </Menu>
    </Col>
  </Row>
</template>

<script>
export default {
  name: 'header-bar'
}
</script>

<style scoped>
  .layout-logo {
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
  }

  .layout-nav {
    width: 420px;
    margin: 0 20px 0 auto;
  }
</style>

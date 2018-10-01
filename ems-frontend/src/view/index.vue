<template>
  <div class="layout">
    <Layout>
      <Header>
        <header-bar></header-bar>
      </Header>
      <Layout :style="{height: 'calc(100% - 60px)'}">
        <Sider>
          <side-menu></side-menu>
        </Sider>
        <Content :style="{padding: '0 20px 20px'}">
          <Header>
            <bread-crumb></bread-crumb>
          </Header>
          <Content :style="{padding: '20px', minHeight: '280px', background: '#fff'}">content</Content>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>
<script>
import HeaderBar from '../components/header-bar'
import SideMenu from '../components/side-menu'
import BreadCrumb from '../components/bread-crumb'
export default {
  data () {
    return {
      accordion: true,
      activeName: 'dist',
      openNames: ['sys']
    }
  },
  components: {
    HeaderBar,
    SideMenu,
    BreadCrumb
  },
  methods: {
    onSelect: function (name) {
      alert(name)
    }
  }
}
</script>
<style scoped>
  .layout {
    background: #f5f7f9;
    position: relative;
    overflow: hidden;
    width: 100%;
    height: 100%;
  }
  .layout-main {

  }
</style>

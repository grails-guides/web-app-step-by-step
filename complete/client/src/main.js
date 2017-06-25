import IceCreamEntry from './ice-cream-entry';
import IceCreamList from './ice-cream-list';
import React, {Component, PropTypes as t} from 'react';
import 'whatwg-fetch';

function changeFlavor(event) {
  React.setState({flavor: event.target.value});
}

function handleError(url, res) {
  React.setState(res.status === 440 ?
    {error: 'Session Timeout', route: 'login'} :
    {error: res.message});
}

async function loadIceCreams(url, headers) {
  let res;
  try {
    res = await fetch(url, {headers});
    if (!res.ok) return handleError(url, res);

    const iceCreams = await res.json();
    const iceCreamMap = {};
    for (const iceCream of iceCreams) {
      iceCreamMap[iceCream.id] = iceCream.flavor;
    }
    React.setState({iceCreamMap});
  } catch (e) {
    handleError(url, res);
  }
}

class Main extends Component {

  static propTypes = {
    flavor: t.string.isRequired,
    iceCreamMap: t.object.isRequired,
    restUrl: t.string.isRequired,
    token: t.string.isRequired,
    username: t.string.isRequired
  };

  /**
   * Gets the current list of ice cream flavors
   * liked by the current user.
   */
  componentDidMount() {
    const {restUrl, token, username} = this.props;

    // This header is used in all REST calls.
    this.headers = {Authorization: token};

    const url = `${restUrl}/ice-cream/${username}`;
    loadIceCreams(url, this.headers);
  }

  /**
   * Adds an ice cream flavor to the list
   * of those liked by the current user.
   */
  addIceCream = async flavor => {
    const {restUrl, username} = this.props;
    const url = `${restUrl}/ice-cream/${username}?flavor=${flavor}`;
    let res;
    try {
      res = await fetch(url, {method: 'POST', headers: this.headers});
      if (!res.ok) return handleError(url, res);
      let id = await res.text();
      if (!id) return;

      // Now that it has been successfully added to the database,
      // add it in the UI.
      id = Number(id);
      const {iceCreamMap} = this.props;
      iceCreamMap[id] = flavor;
      React.setState({flavor: '', iceCreamMap});
    } catch (e) {
      handleError(url, res);
    }
  };

  /**
   * Deletes an ice cream flavor from the list
   * of those liked by the current user.
   */
  deleteIceCream = async id => {
    const {restUrl, username} = this.props;
    const url = `${restUrl}/ice-cream/${username}/${id}`;
    let res;
    try {
      res = await fetch(url, {method: 'DELETE', headers: this.headers});
      if (!res.ok) return handleError(url, res);

      // Now that it has been successfully deleted from the database,
      // delete it from the UI.
      const {iceCreamMap} = this.props;
      delete iceCreamMap[id];
      React.setState({iceCreamMap});
    } catch (e) {
      handleError(url, res);
    }
  };

  render() {
    const {flavor, iceCreamMap, username} = this.props;
    return (
      <div className="main">
        <IceCreamEntry
          addCb={this.addIceCream}
          changeCb={changeFlavor}
          flavor={flavor}
        />
        <label>{username}&apos;s favorite flavors are:</label>
        <IceCreamList
          deleteCb={this.deleteIceCream}
          iceCreamMap={iceCreamMap}
        />
      </div>
    );
  }
}

export default Main;

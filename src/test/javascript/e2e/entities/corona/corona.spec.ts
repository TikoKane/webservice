import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CoronaComponentsPage, CoronaDeleteDialog, CoronaUpdatePage } from './corona.page-object';

const expect = chai.expect;

describe('Corona e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let coronaComponentsPage: CoronaComponentsPage;
  let coronaUpdatePage: CoronaUpdatePage;
  let coronaDeleteDialog: CoronaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Coronas', async () => {
    await navBarPage.goToEntity('corona');
    coronaComponentsPage = new CoronaComponentsPage();
    await browser.wait(ec.visibilityOf(coronaComponentsPage.title), 5000);
    expect(await coronaComponentsPage.getTitle()).to.eq('Coronas');
    await browser.wait(ec.or(ec.visibilityOf(coronaComponentsPage.entities), ec.visibilityOf(coronaComponentsPage.noResult)), 1000);
  });

  it('should load create Corona page', async () => {
    await coronaComponentsPage.clickOnCreateButton();
    coronaUpdatePage = new CoronaUpdatePage();
    expect(await coronaUpdatePage.getPageTitle()).to.eq('Create or edit a Corona');
    await coronaUpdatePage.cancel();
  });

  it('should create and save Coronas', async () => {
    const nbButtonsBeforeCreate = await coronaComponentsPage.countDeleteButtons();

    await coronaComponentsPage.clickOnCreateButton();

    await promise.all([
      coronaUpdatePage.setNombrecasparjourInput('nombrecasparjour'),
      coronaUpdatePage.setCaspositifInput('caspositif'),
      coronaUpdatePage.setCascommunautaireInput('cascommunautaire'),
      coronaUpdatePage.setCasgraveInput('casgrave'),
      coronaUpdatePage.setGuerisonInput('guerison'),
      coronaUpdatePage.setDecesInput('deces'),
      coronaUpdatePage.setCascontactInput('cascontact'),
      coronaUpdatePage.setCasimporteInput('casimporte'),
    ]);

    expect(await coronaUpdatePage.getNombrecasparjourInput()).to.eq(
      'nombrecasparjour',
      'Expected Nombrecasparjour value to be equals to nombrecasparjour'
    );
    expect(await coronaUpdatePage.getCaspositifInput()).to.eq('caspositif', 'Expected Caspositif value to be equals to caspositif');
    expect(await coronaUpdatePage.getCascommunautaireInput()).to.eq(
      'cascommunautaire',
      'Expected Cascommunautaire value to be equals to cascommunautaire'
    );
    expect(await coronaUpdatePage.getCasgraveInput()).to.eq('casgrave', 'Expected Casgrave value to be equals to casgrave');
    expect(await coronaUpdatePage.getGuerisonInput()).to.eq('guerison', 'Expected Guerison value to be equals to guerison');
    expect(await coronaUpdatePage.getDecesInput()).to.eq('deces', 'Expected Deces value to be equals to deces');
    expect(await coronaUpdatePage.getCascontactInput()).to.eq('cascontact', 'Expected Cascontact value to be equals to cascontact');
    expect(await coronaUpdatePage.getCasimporteInput()).to.eq('casimporte', 'Expected Casimporte value to be equals to casimporte');

    await coronaUpdatePage.save();
    expect(await coronaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await coronaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Corona', async () => {
    const nbButtonsBeforeDelete = await coronaComponentsPage.countDeleteButtons();
    await coronaComponentsPage.clickOnLastDeleteButton();

    coronaDeleteDialog = new CoronaDeleteDialog();
    expect(await coronaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Corona?');
    await coronaDeleteDialog.clickOnConfirmButton();

    expect(await coronaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
